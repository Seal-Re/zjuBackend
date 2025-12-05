package com.hengtiansoft.fastop.base.common.constants.Status;

public interface StatusContants {
	
	public static final Integer device_status_invalid = 0;	// 无效
	public static final Integer device_status_available = 1;	// 可用
	public static final Integer device_status_busy = 2;	// 繁忙
	public static final Integer device_status_fault = 3;	// 故障
	
	// 测试集审签类型
	public static final int suite_app_type_fun = 1;	// 模块审签
	public static final int suite_app_type_list = 2;	// 清单审签
	
	// 测试集模块类型
	public static final int tfun_type_general = 0;	// 一般测试模块
	public static final int tfun_type_keygeneral = 1;	// 关键一般测试模块
	public static final int tfun_type_military = 2;	// 军检测模块
	public static final int tfun_type_keymilitary = 3;	// 关键军检测试模块
	
	// 测试集模块集审签状态
	public static final int suite_funs_app_unapp = 0;	// 未审签
	public static final int suite_funs_app_passed = 1;	// 审签通过
//	public static final int suite_funs_app_proof = 2;	// 待校对
//	public static final int suite_funs_app_verify = 3;	// 待审核
//	public static final int suite_funs_app_qualityapp = 4;	// 待质审
//	public static final int suite_funs_app_checkapp = 5;	// 待审查
//	public static final int suite_funs_app_militaryapp = 6;	// 待军审
//	public static final int suite_funs_app_approve = 7;	// 待批准
	
	// 测试集清单审签状态
	public static final int suite_list_app_approve_fail = -3;	// 批准失败
	public static final int suite_list_app_proof_fail = -2;	// 校对失败
	public static final int suite_list_app_unapp = 0;	// 未审签
	public static final int suite_list_app_passed = 1;	// 审签通过
	public static final int suite_list_app_proof = 2;	// 待校对
	public static final int suite_list_app_approve = 3;	// 待批准

	//测试集审签状态
//	public static final int  suite_app_unpass = 0; //未审签
//	public static final int  suite_app_passed = 1; //审签通过
//	public static final int  suite_app_verifing = 2; //待审签
//	public static final int  suite_app_client_verifing = 3;	//待客户审签
	
	//测试功能的审签状态
	public static final int  tfun_app_approve_fail = -7;	// 待批准失败
//	public static final int  tfun_app_militaryapp_fial = -6;	// 待军审失败
	public static final int  tfun_app_checkapp_fial = -5;	// 待审查失败
	public static final int  tfun_app_qualityapp_fial = -4;	// 待质审失败
	public static final int  tfun_app_verify_fial = -3; // 待审核失败
	public static final int  tfun_app_proof_fial = -2; // 待校对失败
	public static final int  tfun_app_unsubmit = 0; // 待确认
	public static final int  tfun_app_passed = 1; // 审签通过
	public static final int  tfun_app_proof = 2; // 待校对
	public static final int  tfun_app_verify = 3; // 待审核
	public static final int  tfun_app_qualityapp = 4;	// 待质审
	public static final int  tfun_app_checkapp = 5;	// 待审查
//	public static final int  tfun_app_militaryapp = 6;	// 待军审
	public static final int  tfun_app_approve = 7;	// 待批准
	
	//测试功能修改标志 changeflag
	public static final int  tfun_cha_unchange = 0; // 未改变
	public static final int  tfun_cha_funinfo = 1; //修改功能基本信息
	public static final int  tfun_cha_step = 2; //修改步骤
	public static final int  tfun_cha_funorder = 3; //调整功能顺序
	public static final int  tfun_cha_newfun = 4; //新建功能
	
	//测试步骤修改标志 changeflag
	public static final int  tstep_cha_unchange = 0; //未改变
	public static final int  tstep_cha_steporder = 1; //调整步骤顺序
	public static final int  tstep_cha_stepinfo = 2; //修改步骤信息
	public static final int  tstep_cha_newstep = 3; //新建步骤
	
	// 步骤层级 level
	public static final int  step_level_module = 1;	// 用例级别
	public static final int  step_level_case = 2;	// 子用例级别
	public static final int  step_level_step = 3;	// 步骤级别
	
	// 步骤判据类型 criterionType
	public static final int  criterion_type_operation = 0;	// 仅操作类型
	public static final int  criterion_type_choose = 1;	// 选值类型
	public static final int  criterion_type_input = 2;	// 输入值类型
	
	// 步骤检验状态 verify status
	public static final int  verify_status_unverify = 0;	// 未检验状态
	public static final int  verify_status_passed = 1;	// 检验通过状态
	public static final int  verify_status_unpass = 2;	// 检验不通过状态
	
	// 步骤结果判定状态 judge status
	public static final int  judge_result_unjudge = 0;	// 未判定
	public static final int  judge_result_passed = 1;	// 判定通过
	public static final int  judge_result_unpass = 2;	// 判定不通过

	// 测试对象类型
	public static final int  unknown = 0;	// 未知
	public static final int  test_plan = 1;	// 测试计划
	public static final int  exe_function = 2;	// 测试功能
	public static final int  exe_case = 3;	// 测试子用例
	public static final int  exe_module = 4;	// 测试模块
	public static final int  exe_step = 5;	// 测试步骤
	
	// 系统测试类型
	public static final Integer system_test = 1;	// 系统测
	public static final Integer cable_test = 2;		//线缆测
	
	// 线缆测试项
	public static final int cable_conduction = 1;	// 导通测试
	public static final int cable_insulation = 2;	// 绝缘测试

	// 线缆测试项
	public static final String CKT_CODE_ADDRESS = "address";	// 导通测试
	public static final String CKT_CODE_CONDUCTION = "conduction";	// 绝缘测试

	// 模块审签
	public static final int funs_app_submit = 0;
	public static final int funs_app_proofer = 1;
	public static final int funs_app_verifier = 2;
	public static final int funs_app_checker = 3;
	public static final int funs_app_qualityer = 4;
	public static final int funs_app_approver = 5;
	public static final int funs_app_success = 6;
	public static final int funs_app_failure = 7;

	public static final String[] FUNS_APP_LEVEL = {"待提交", "待校对", "待质审", "待审查", "待批准", "待审核", "审签成功", "审签失败"};

	// 清单审签
	public static final int suite_app_submit = 0;
	public static final int suite_app_proofer = 1;
	public static final int suite_app_approver = 2;
	public static final int suite_app_success = 3;
	public static final int suite_app_failure = 4;

	public static final String[] SUITE_APP_LEVEL = {"待提交", "待校对", "待批准", "审签成功", "审签失败"};


}
