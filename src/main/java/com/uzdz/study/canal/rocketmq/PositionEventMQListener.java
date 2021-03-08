package com.uzdz.study.canal.rocketmq;

import com.qiyee.job.rocketmq.consumer.AbstractRocketConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class PositionEventMQListener extends AbstractRocketConsumer {

	@Override
	public String consumerGroup() {
		return "data-sync-d2";
	}

	@Override
	public String topic() {
		return "qiyee_job_position_t1";
	}

	@Override
	public String tags() {
		return "*";
	}

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		msgs.forEach(msg -> {

			DEvent dEvent = MsgConvertUtils.msgConvert(new String(msg.getBody()));

			if (dEvent == null) {
				log.error("数据解析错误！消息Body：" + new String(msg.getBody()));
			} else {
				switch(dEvent.getType()) {
					case "UPDATE" :
						EventUpdate(dEvent);
						break;
					case "INSERT" :
						EventInsert(dEvent);
						break;
					case "DELETE" :
						EventDelete(dEvent);
						break;
					default :
						log.info("无用操作，丢弃！");
				}
			}
		});

		// ACK 标记该消息已经被成功消费
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	/**
	 * 新增数据
	 * @param dEvent
	 */
	public void EventInsert(DEvent dEvent) {

		try {
			List<Map<String, String>> rowDatasList = dEvent.getDynamicDataList();

			for (Map<String, String> rowData : rowDatasList) {

				Map<String, Object> insert = new HashMap<>();
				AtomicReference<String> id = new AtomicReference<>();
				Map<String, Double> geoMap = new HashMap<>(2);

				for(Map.Entry<String, String> row : rowData.entrySet()) {
					if (row.getKey().equals(dEvent.getPkName())) {
						id.set(row.getValue());
					}

					String lonStr=row.getKey();
					setLocationGeo(geoMap, row);
					insert.put(lonStr, row.getValue());
				}

				if (id.get() == null) {
					continue;
				}
				if (!geoMap.isEmpty()){
					insert.put("locationGeo",geoMap);
				}
			}
		} catch (Exception e) {
			log.error("[Insert] 发生异常：" + e.getMessage(), e);
		}

		log.info("\n======================================================");
	}

	/**
	 * 更新数据
	 * @param dEvent
	 */
	public void EventUpdate(DEvent dEvent) {

		try {
			List<Map<String, String>> rowDatasList = dEvent.getDynamicDataList();

			for (Map<String, String> rowData : rowDatasList) {
				Map<String, Object> update = new HashMap<>();
				AtomicReference<String> id = new AtomicReference<>();
				Map<String, Double> geoMap = new HashMap<>(2);

				for(Map.Entry<String, String> row : rowData.entrySet()) {
					if (row.getKey().equals(dEvent.getPkName())) {
						id.set(row.getValue());
					}

					setLocationGeo(geoMap, row);
					update.put(row.getKey(), row.getValue());
				};

				if (id.get() == null) {
					continue;
				}

				if (!geoMap.isEmpty()){
					update.put("locationGeo",geoMap);
				}
			}
		} catch (Exception e) {
			log.error("[Update] 发生异常：" + e.getMessage(), e);
		}

		log.info("\n======================================================");
	}

	/**
	 * 删除数据
	 * @param dEvent
	 */
	public void EventDelete(DEvent dEvent) {

		try {
			List<Map<String, String>> rowDatasList = dEvent.getDynamicDataList();

			for (Map<String, String> rowData : rowDatasList) {

				AtomicReference<String> id = new AtomicReference<>();
				for(Map.Entry<String, String> row : rowData.entrySet()) {
					if (row.getKey().equals(dEvent.getPkName())) {
						id.set(row.getValue());
						break;
					}
				};

				if (id.get() == null) {
					continue;
				}

			}
		} catch (Exception e) {
			log.error("[Delete] 发生异常：" + e.getMessage(), e);
		}

		log.info("\n======================================================");
	}

	private void setLocationGeo(Map<String,Double> dataMap, Map.Entry<String, String> row){

		String lonStr=row.getKey();
		if (lonStr != null &&"longitude".equals(lonStr)&& StringUtils.hasText(row.getValue())) {
			dataMap.put("lon", Double.parseDouble(row.getValue()));
		}

		if (lonStr != null &&"latitude".equals(lonStr)&& StringUtils.hasText(row.getValue())) {
			dataMap.put("lat", Double.parseDouble(row.getValue()));
		}
		return;
	}
}
