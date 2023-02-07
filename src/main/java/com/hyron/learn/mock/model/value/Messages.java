package com.hyron.learn.mock.model.value;

public enum Messages implements EnumMessages {

  SE0001("Unexpected error has occurred. message: {0}."),
  BE0001("Can''t find user by login id: {0}."),
  BE0002("Can''t create user with login id: {0}."),
  BE0003("completeFlag must be not null."),
  VE001("2日以前で注文した伝票はキャンセルできません。slipNumber:{0}"),
  VE002("当該伝票はすでにキャンセル済み。slipNumber: {0}"),
  VE003("2日以前で注文した明細はキャンセルできません。slipNumber:{0}, lineNumber: {1}"),
  VE004("当該明細はすでにキャンセル済み。slipNumber: {0}, lineNumber: {1}"),
  VE005("非営業期間で処理できません。(営業期間 08:00~20:00)"),
  ;

  private final String code;
  private final String description;

  Messages(String description) {
    this.code = this.name();
    this.description = description;
  }

  Messages(String code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public String toString() {
    return this.description;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getDescription() {
    return this.description;
  }
}
