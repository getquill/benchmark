package benchmark.quill

import io.getquill._
import java.util.Date

object Schema {

  val part = quote(query[Part])

  case class Part(
    p_partkey: Int,
    p_name: String,
    p_mfgr: String,
    p_brand: String,
    p_type: String,
    p_size: Int,
    p_container: String,
    p_retailprice: BigDecimal,
    p_comment: String)

  val supplier = quote(query[Supplier])

  case class Supplier(
    s_suppkey: Int,
    s_name: String,
    s_address: String,
    s_nationkey: Int,
    s_phone: String,
    s_acctbal: BigDecimal,
    s_comment: String)

  val partsupp = quote(query[Partsupp])

  case class Partsupp(
    ps_partkey: Int,
    ps_suppkey: Int,
    ps_availqty: Int,
    ps_supplycost: BigDecimal,
    ps_comment: String)

  val customer = quote(query[Customer])

  case class Customer(
    c_custkey: Int,
    c_name: String,
    c_address: String,
    c_nationkey: Int,
    c_phone: String,
    c_acctbal: BigDecimal,
    c_mktsegment: String,
    c_comment: String)

  val orders = quote(query[Orders])

  case class Orders(
    o_orderkey: Int,
    o_custkey: Int,
    o_orderstatus: String,
    o_totalprice: BigDecimal,
    o_orderdate: Date,
    o_orderpriority: String,
    o_clerk: String,
    o_shippriority: Int,
    o_comment: String)

  val lineitem = quote(query[Lineitem])

  case class Lineitem(
    l_orderkey: Int,
    l_partkey: Int,
    l_suppkey: Int,
    l_linenumber: Int,
    l_quantity: BigDecimal,
    l_extendedprice: BigDecimal,
    l_discount: BigDecimal,
    l_tax: BigDecimal,
    l_returnflag: String,
    l_linestatus: String,
    l_shipdate: Date,
    l_commitdate: Date,
    l_receiptdate: Date,
    l_shipinstruct: String,
    l_shipmode: String,
    l_comment: String)

  val nation = quote(query[Nation])

  case class Nation(
    n_nationkey: Int,
    n_name: String,
    n_regionkey: Int,
    n_comment: String)

  val region = quote(query[Region])

  case class Region(
    r_regionkey: Int,
    r_name: String,
    r_comment: String)
}
