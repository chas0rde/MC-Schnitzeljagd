package de.hsb.kss.mc_schnitzeljagd.logic;

import org.apache.commons.lang3.RandomStringUtils;

public class AccessCodeGenerator {

	public String getGeneratedAccessCode() {
		String startSequence=RandomStringUtils.randomAlphanumeric(4);
		String endSequence=RandomStringUtils.randomAlphanumeric(4);
		return startSequence+"hsb"+endSequence;
	}
}
