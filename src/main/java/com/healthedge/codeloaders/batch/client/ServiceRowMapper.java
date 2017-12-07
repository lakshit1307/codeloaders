package com.healthedge.codeloaders.batch.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.healthedge.codeloaders.entity.Service;

public class ServiceRowMapper implements RowMapper<Service> {

	@Override
	public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
		Service service=new Service();
		service.setServiceCode(rs.getString("SERV_CD"));
		service.setServiceTypeCD(rs.getString("SERV_TYPE_CD"));
		service.setServiceShortDesciption(rs.getString("SERV_SHORT_DSC"));
		service.setServiceLongDesciption(rs.getString("SERV_LONG_DSC"));
		service.setServiceAlternateDesciption(rs.getString("ALT_DSC"));
		service.setEffectiveStartDate(rs.getDate("EFF_START_DT"));
		service.setEffectiveEndDate(rs.getDate("EFF_END_DT"));
		service.setStandardizedServiceCode(rs.getString("STANDARDIZED_SERV_CD"));
		service.setworkFlowCode(rs.getString("WRK_FLOW_CD"));
		service.setTxCnt(rs.getLong("TX_CNT"));
		service.setLastTransactionDate(rs.getDate("LAST_TX_DT"));
		service.setLastTransactionUserText(rs.getString("LAST_TX_USER_TXT"));
		service.setCodeProcessingHistoryId(rs.getInt("CODE_PROCESSING_HISTORY_ID"));
		service.setVersion(rs.getDate("VERSION"));
		service.setAction(rs.getString("ACTION"));
		return service;
	}

}
