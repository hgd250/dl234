package com.my120.market.card.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.BatchException;
import com.ibatis.sqlmap.engine.execution.BatchResult;
import com.my120.commons.ResultList;
import com.my120.market.card.dao.ICardDAO;
import com.my120.market.model.CardQueryModel;

/**
 * 
 * @author cai 2012-11-14
 */
public class CardDAOImpl extends SqlMapClientDaoSupport implements ICardDAO {
    @Override
    public void insertCard(CardQueryModel query) {
        this.getSqlMapClientTemplate().insert("insertCard", query);
    }

    @Override
    public String getMaxCardNumber(CardQueryModel query) {
        return (String) this.getSqlMapClientTemplate().queryForObject("getMaxCardNumber", query);
    }

    @Override
    public int batchInsert(final List<String> cardNumberList, final CardQueryModel card) throws SQLException {
        SqlMapClient client = this.getSqlMapClient();
        try {
            client.startTransaction();
            client.startBatch();
            for (String cardNumber : cardNumberList) {
                card.setNumber(cardNumber);
                client.insert("insertCard", card);
            }
            return client.executeBatch();
        } finally {
            client.commitTransaction();
            client.endTransaction();
        }
    }

    @SuppressWarnings("unchecked")
    public ResultList<CardQueryModel> selectCardInfoList(Map<String, Object> paramMap) {
        Integer start = (Integer) paramMap.get("start");
        Integer end = (Integer) paramMap.get("end");

        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("selectCardInfoCount", paramMap);
        if (start != null && end != null && start >= count && count > 0) {
            double round = Math.ceil((double) count / (double) end);
            int sumpage = (int) round;
            start = ((sumpage - 1) > 0 ? sumpage - 1 : 0) * end;
        }

        paramMap.put("start", start);
        paramMap.put("end", end);
        List<CardQueryModel> list = getSqlMapClientTemplate().queryForList("selectCardInfoList", paramMap);
        ResultList<CardQueryModel> result = new ResultList<CardQueryModel>();
        result.setResult(list.toArray(new CardQueryModel[] {}));
        result.setSize(count);
        return result;
    }

    @Override
    public CardQueryModel selectCardInfoByCardNumber(CardQueryModel query) {
        return (CardQueryModel) getSqlMapClientTemplate().queryForObject("selectCardInfoByCardNumber", query);
    }

    @Override
    public int updateCard(CardQueryModel query) {
        return getSqlMapClientTemplate().update("updateCard", query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CardQueryModel> batchCancelCard(List<CardQueryModel> list) throws Exception {
        SqlMapClient objClient = this.getSqlMapClient();
        List<CardQueryModel> returnList = new ArrayList<CardQueryModel>();
        try {
            objClient.startTransaction();
            objClient.startBatch();

            long time = System.currentTimeMillis();
            for (CardQueryModel card : list) {
                card.setStatus(CardQueryModel.CARD_STATUS_5);
                card.setDestroyedTime(time);
                card.setGmtModify(time);
                objClient.update("cancelCard", card);
            }
            List<BatchResult> batchDetailed = objClient.executeBatchDetailed();
            int[] counts = batchDetailed.get(0).getUpdateCounts();
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] == 0) {
                    returnList.add(list.get(i));
                }
            }
        } catch (SQLException e) {
            throw e;
        } catch (BatchException e) {
            throw e;
        } finally {
            objClient.commitTransaction();
            objClient.endTransaction();
        }
        return returnList;
    }
}
