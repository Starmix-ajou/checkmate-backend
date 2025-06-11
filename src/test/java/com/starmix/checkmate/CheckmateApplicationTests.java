package com.starmix.checkmate;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Checkmate 전체 테스트 스위트")
@SelectPackages({
		"com.starmix.checkmate.domain",
		"com.starmix.checkmate.adapter",
})
public class CheckmateApplicationTests {

	@Test
	void contextLoads() {
	}
}