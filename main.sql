CREATE TABLE "CODE_FILE_STATUS"
   (    "FILE_TYPE" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "FILE_NAME" VARCHAR2(50 BYTE),
    "STATUS" VARCHAR2(50 BYTE),
    "VERSION" NUMBER NOT NULL ENABLE,
    "TX_DATE" TIMESTAMP (6) NOT NULL ENABLE,
    "TX_CNT" NUMBER NOT NULL ENABLE,
     CONSTRAINT "CODE_FILE_STATUS_PK" PRIMARY KEY ("TX_CNT")
);

CREATE TABLE "T_SERVICE"
   (    "SERV_CD" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "SERV_TYPE_CD" VARCHAR2(3 BYTE) NOT NULL ENABLE,
    "SERV_SHORT_DSC" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "SERV_LONG_DSC" VARCHAR2(1000 BYTE),
    "ALT_DSC" VARCHAR2(1000 BYTE),
    "EFF_START_DT" DATE NOT NULL ENABLE,
    "EFF_END_DT" DATE,
    "STANDARDIZED_SERV_CD" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "WRK_FLOW_CD" VARCHAR2(2 BYTE),
    "TX_CNT" NUMBER NOT NULL ENABLE,
    "LAST_TX_DT" TIMESTAMP (6) NOT NULL ENABLE,
    "LAST_TX_USER_TXT" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "CODE_PROCESSING_HISTORY_ID" NUMBER NOT NULL ENABLE,
    "VERSION_START" NUMBER NOT NULL ENABLE,
    "VERSION_END" NUMBER NOT NULL,
    "ACTION" VARCHAR2(50 BYTE) NOT NULL ENABLE,
     CONSTRAINT "T_SERVICE_UK" UNIQUE ("SERV_CD", "VERSION_START", "VERSION_END")
);

CREATE TABLE "T_DIAGNOSIS"
   (	"DIAG_CD" VARCHAR2(255 BYTE) NOT NULL ENABLE,
	"DIAG_TYPE_CD" VARCHAR2(1 BYTE) NOT NULL ENABLE,
	"DIAG_SHORT_DSC" VARCHAR2(50 BYTE) NOT NULL ENABLE,
	"DIAG_LONG_DSC" VARCHAR2(1000 BYTE) NOT NULL ENABLE,
	"ALT_DSC" VARCHAR2(1000 BYTE),
	"DIAG_CAT_DSC" VARCHAR2(50 BYTE),
	"EFF_START_DT" DATE NOT NULL ENABLE,
	"EFF_END_DT" DATE,
	"STANDARDIZED_DIAG_CD" VARCHAR2(255 BYTE) NOT NULL ENABLE,
	"WRK_FLOW_CD" VARCHAR2(2 BYTE),
	"TX_CNT" NUMBER NOT NULL ENABLE,
	"LAST_TX_DT" TIMESTAMP (6) NOT NULL ENABLE,
	"LAST_TX_USER_TXT" VARCHAR2(50 BYTE) NOT NULL ENABLE,
	"CAT_NM" VARCHAR2(255 BYTE),
	"LEVEL2_DIAG_CAT_CAT_NM" VARCHAR2(255 BYTE),
	"LEVEL3_DIAG_CAT_CAT_NM" VARCHAR2(255 BYTE),
	"LEVEL4_DIAG_CAT_CAT_NM" VARCHAR2(255 BYTE),
	"VERSION_START" NUMBER NOT NULL ENABLE,
	"VERSION_END" NUMBER NOT NULL ENABLE,
	"ACTION" VARCHAR2(50 BYTE) NOT NULL ENABLE,
	 CONSTRAINT "T_DIAG" PRIMARY KEY ("DIAG_CD", "VERSION_START", "VERSION_END")
   );
