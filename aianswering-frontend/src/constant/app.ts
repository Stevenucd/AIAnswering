// 审核状态枚举
export const REVIEW_STATUS_ENUM = {
  // Pending
  REVIEWING: 0,
  // Pass
  PASS: 1,
  // Reject
  REJECT: 2,
};

// 审核状态映射
export const REVIEW_STATUS_MAP = {
  0: "Pending",
  1: "Pass",
  2: "Reject",
};

// 应用类型枚举
export const APP_TYPE_ENUM = {
  // Grading Type
  SCORE: 0,
  // Assessment Type
  TEST: 1,
};

// 应用类型映射
export const APP_TYPE_MAP = {
  0: "Grading Type",
  1: "Assessment Type",
};

// 应用评分策略枚举
export const APP_SCORING_STRATEGY_ENUM = {
  // 自定义
  CUSTOM: 0,
  // AI
  AI: 1,
};

// 应用评分策略映射
export const APP_SCORING_STRATEGY_MAP = {
  0: "Custom",
  1: "AI",
};
