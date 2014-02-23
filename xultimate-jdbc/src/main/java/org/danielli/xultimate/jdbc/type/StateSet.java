package org.danielli.xultimate.jdbc.type;


/**
 * 替换MySQL当中的BIT或SET数据类型。
 * 
 * <pre>
 * 	计划：采用0-127，支持8个类型，可表示为128种不同状态
 * 	这样查询时完整匹配使用SELECT C_XX FROM T_XX WHERE C_XX = ?形式;
 *  需要进行部分查询，如查询某一类型的本可以使用SELECT C_XX FROM T_XX WHERE C_XX & ?，但这种方式无法应用索引。
 *  所以想通过SELECT C_XX FROM T_XX WHERE C_XX IN (?, ?..)这种形式来完成。
 *  如3(0011)对应(3, 7(0111), 11(1011), 15(1111)...)
 * </pre> 
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class StateSet {

	private byte value;
	
	public StateSet(byte value) {
		this.value = value;
	}
	
	public StateSet() {
		clear();
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}
	
	public void clear() {
		value = 0;
	}
	
	public void add(byte state) {
		value = (byte) (value | state);
	}
	
	public void remove(byte state) {
		value = (byte) (value & (~state));
	}
	
	public boolean contain(byte state) {
		return (value & state) == state;
	}
}
