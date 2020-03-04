package com.transfar.domain.server;

import com.transfar.common.SuperBean;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 内存信息
 * </p>
 *
 * @author 皮锋
 * @custom.date 2020年3月3日 下午2:20:14
 */
@Data
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MemoryDomain extends SuperBean {

	/**
	 * 物理内存总量
	 */
	private String memTotal;
	/**
	 * 物理内存使用量
	 */
	private String memUsed;
	/**
	 * 物理内存剩余量
	 */
	private String memFree;

	/**
	 * 物理内存使用率
	 */
	private String menUsedPercent;

	@Override
	public String toString() {
		return "MemoryVo [物理内存总量=" + memTotal + ", 物理内存使用量=" + memUsed + ", 物理内存剩余量=" + memFree + ", 物理内存使用率="
				+ menUsedPercent + "]";
	}

}
