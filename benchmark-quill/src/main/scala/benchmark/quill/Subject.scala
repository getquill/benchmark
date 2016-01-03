package benchmark.quill

import io.getquill._
import java.util.Date
import io.getquill.source.sql.mirror.mirrorSource

object Subject extends App {

  import Schema._

  implicit class RichDate(a: Date) {
    def >=(b: Date) = quote(infix"$a >= $b".as[Boolean])
    def <=(b: Date) = quote(infix"$a <= $b".as[Boolean])
    def <(b: Date) = quote(infix"$a < $b".as[Boolean])
    def >(b: Date) = quote(infix"$a > $b".as[Boolean])
  }

  val shipDate = quote {
    (delta: Int) => infix"date '1998-12-01' - interval '$delta days'".as[Date]
  }

  val q1 = quote {
    (delta: Int) =>
      lineitem
        .filter(l => l.l_shipdate <= shipDate(delta))
        .sortBy(l => (l.l_returnflag, l.l_linestatus))
        .groupBy(l => (l.l_returnflag, l.l_linestatus))
        .map {
          case ((returnFlag, status), lines) =>
            (
              returnFlag,
              status,
              lines.map(_.l_quantity).sum,
              lines.map(_.l_extendedprice).sum,
              lines.map(l => l.l_extendedprice * (1 - l.l_discount)).sum,
              lines.map(l => l.l_extendedprice * (1 - l.l_discount) * (1 + l.l_tax)).sum,
              lines.map(_.l_quantity).avg,
              lines.map(_.l_extendedprice).avg,
              lines.map(_.l_discount).avg,
              lines.size)
        }
  }

  mirrorSource.run(q1)

  implicit class Like(a: String) {
    def like(b: String) = quote(infix"$a like $b".as[Boolean])
  }

  val q2 = quote {
    (size: Int, typ: String, reg: String) =>
      for {
        p <- part
        s <- supplier
        ps <- partsupp
        n <- nation
        r <- region
        if p.p_partkey == ps.ps_partkey &&
          s.s_suppkey == ps.ps_suppkey &&
          p.p_size == size &&
          s.s_nationkey == n.n_nationkey &&
          n.n_regionkey == r.r_regionkey &&
          r.r_name == reg &&
          ps.ps_supplycost ==
          (for {
            ps <- partsupp
            s <- supplier
            n <- nation
            r <- region
            if p.p_partkey == ps.ps_partkey &&
              s.s_suppkey == ps.ps_suppkey &&
              s.s_nationkey == n.n_nationkey &&
              n.n_regionkey == r.r_regionkey &&
              r.r_name == reg
          } yield {
            ps.ps_supplycost
          }).min
      } yield {
        (
          s.s_acctbal,
          s.s_name,
          n.n_name,
          p.p_partkey,
          p.p_mfgr,
          s.s_address,
          s.s_phone,
          s.s_comment)
      }
  }

  mirrorSource.run(q2)

  val q3 = quote {
    (segment: String, date: Date) =>
      (for {
        c <- customer
        o <- orders
        l <- lineitem
        if c.c_mktsegment == segment &&
          c.c_custkey == o.o_custkey &&
          l.l_orderkey == o.o_orderkey &&
          o.o_orderdate < date &&
          l.l_shipdate > date
      } yield {
        (c, o, l)
      }).groupBy {
        case (c, o, l) =>
          (l.l_orderkey, o.o_orderdate, o.o_shippriority)
      }.map {
        case ((l_orderkey, o_orderdate, o_shippriority), list) =>
          (l_orderkey, list.map { case (c, o, l) => l.l_extendedprice * (1 - l.l_discount) }.sum, o_orderdate, o_shippriority)
      }.sortBy {
        case (l_orderkey, revenue, o_orderdate, o_shippriority) =>
          (revenue, o_orderdate)
      }(Ord(Ord.desc, Ord.asc))
  }

  mirrorSource.run(q3)
}
