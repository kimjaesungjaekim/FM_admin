package com.developer.fillme.service.impl;

import com.developer.fillme.constant.EPolicyType;
import com.developer.fillme.exception.BaseException;
import com.developer.fillme.service.IPolicyService;
import org.springframework.stereotype.Service;

import static com.developer.fillme.constant.EException.TYPE_FOUND;

@Service
public class IPolicyServiceImpl implements IPolicyService {
	@Override
	public String getTemplatesPolicy(EPolicyType type) {
		return switch (type) {
			case TERM_OF_USE -> "policy/term-of-use";
			case PRIVACY_POLICY -> "policy/privacy-policy";
			case SENSITIVE_INFORMATION -> "policy/sensitive-information";
			case MARKETING -> "policy/marketing";
			case OPERATION_SERVICE -> "policy/operation-service";
			default -> throw new BaseException(TYPE_FOUND);
		};
	}
}
