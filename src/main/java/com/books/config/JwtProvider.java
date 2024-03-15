package com.books.config;

import com.books.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;

public class JwtProvider {

  private final String secretKey;

  private final long tokenValidTime = 1000L * 60 * 60 * 24;

  public JwtProvider(@Value("${encryption.key}") String secretKey) {
    this.secretKey = secretKey;
  }

  public String createToken(String nickname, Long id) {
    Claims claims = Jwts.claims().setSubject(AES256Util.encrypt(nickname))
        .setId(AES256Util.encrypt(id.toString()));
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public boolean validateToken(String jwtToken) {
    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .setSigningKey(secretKey).parseClaimsJws(jwtToken);

      return !claimsJws.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public UserVo getUserVo(String token) {
    Claims claims = Jwts.parser().setSigningKey(secretKey)
        .parseClaimsJws(token).getBody();

    return new UserVo(Long.valueOf(
        Objects.requireNonNull(AES256Util.decrypt(claims.getId()))),
    AES256Util.decrypt(claims.getSubject()));
  }
}
