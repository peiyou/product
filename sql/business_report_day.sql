use report;
drop table business_report_day;
CREATE TABLE `business_report_day` (
  `statis_date` int(11) NOT NULL COMMENT '统计时间',
  `type` int(2) DEFAULT '1' COMMENT '类型1表示按天',
  `fundbtc_count` int(10) DEFAULT NULL COMMENT '充值比特币次数',
  `fundbtc_amount` decimal(18,8) DEFAULT NULL COMMENT '充值比特币金额',
  `fundbtc_users` int(10) DEFAULT NULL COMMENT '充值比特币人数',
  `withdrawbtc_count` int(10) DEFAULT NULL COMMENT '提现比特币次数',
  `withdrawbtc_amount` decimal(18,8) DEFAULT NULL COMMENT '提现比特币总量',
  `withdrawbtc_fee` decimal(18,8) DEFAULT NULL COMMENT '提现比特币手续费',
  `withdrawbtc_users` int(10) DEFAULT NULL COMMENT '提现比特币人数',
  `fundcny_count` int(10) DEFAULT NULL COMMENT '充值人民币次数',
  `fundcny_amount` decimal(18,8) DEFAULT NULL COMMENT '充值人民币总量',
  `fundcny_users` int(10) DEFAULT NULL COMMENT '充值人民币人数',
  `withdrawcny_count` int(10) DEFAULT NULL COMMENT '提现人民币次数',
  `withdrawcny_amount` decimal(18,8) DEFAULT NULL COMMENT '提现人民币金额',
  `withdrawcny_fee` decimal(18,8) DEFAULT NULL COMMENT '提现人民币手续费',
  `withdrawcny_users` int(10) DEFAULT NULL COMMENT '提现人民币用户数',
  `trade_users` int(10) DEFAULT NULL COMMENT '交易用户数',
  `trade_amount` decimal(18,8) DEFAULT NULL COMMENT '交易量',
  `api_trade_amount` decimal(18,8) DEFAULT NULL COMMENT 'api交易量',
  `api_trade_users` int(10) DEFAULT NULL COMMENT 'api交易人数',
  `borrowed_cny_users` int(10) DEFAULT NULL COMMENT '借贷人民币用户数',
  `borrowed_cny_amount` decimal(18,8) DEFAULT NULL COMMENT '借贷人民币量',
  `borrowed_btc_users` int(10) DEFAULT NULL COMMENT '借货比特币用户数',
  `borrowed_btc_amount` decimal(18,8) DEFAULT NULL COMMENT '借贷比特币量',
  `borrowed_cny_interest_collected` decimal(18,8) DEFAULT NULL COMMENT '人民币借贷利息（已收取）',
  `borrowed_btc_interest_collected` decimal(18,8) DEFAULT NULL COMMENT '比特币借贷利息（已收取）'
)


