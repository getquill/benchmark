CREATE TABLE LINEITEM(
	L_ORDERKEY integer,
	L_PARTKEY integer,
	L_SUPPKEY integer,
	L_LINENUMBER integer,
	L_QUANTITY decimal(12,6),
	L_EXTENDEDPRICE decimal(12,6),
	L_DISCOUNT decimal(12,6),
	L_TAX decimal(12,6),
	L_RETURNFLAG varchar(1),
	L_LINESTATUS varchar(1),
	L_SHIPDATE date,
	L_COMMITDATE date,
	L_RECEIPTDATE date,
	L_SHIPINSTRUCT varchar(25),
	L_SHIPMODE varchar(10),
	L_COMMENT varchar(44)
);