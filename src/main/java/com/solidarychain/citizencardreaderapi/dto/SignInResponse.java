package com.solidarychain.citizencardreaderapi.dto;

// {"data":{"signIn":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJlNTBlYmE5YS1mZmZhLTQ1MTYtYWYwMS05ZmRjZWExNjRmZmMiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTYzNDU5Mzg5N30.hJL9C06r8lN3U3Glf4m5sTnji0bltCz_bP2yUghNtlA"}}

public class SignInResponse {
  private SignInDataResponse data;

  public SignInDataResponse getData() {
    return this.data;
  }

  public void setData(SignInDataResponse data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return this.data.getSignIn();
  }
}
