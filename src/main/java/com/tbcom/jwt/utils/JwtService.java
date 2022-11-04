package com.tbcom.jwt.utils;

import com.tbcom.login.service.impl.UserVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class JwtService {

    public enum JwtUtils {
        ACCESS_KEY(1,1L),
        REFRESH_KEY(2,60*1000L);

        private final int index;
        private final String privateKey;
        private final Long time;

        JwtUtils(int index, Long time) {
            this.index = index;
            this.privateKey = randomKey();
            this.time = time;
        }

    }

    // key
    private static final String DATA_KEY = "user";

    public boolean check = true;
    public String access = "";
    public String refresh = "";
    public Map<String,String> errMsg = new HashMap<>();


    public void createAccessToken(UserVO user) {
        this.access = this.createToken(user,JwtUtils.ACCESS_KEY.name());
    }

    public void createRefreshToken(UserVO user) {
        this.refresh = this.createToken(user,JwtUtils.REFRESH_KEY.name());
    }

    //비밀키 생성 메소드
    private byte[] generateKey(String privateKey){
        return privateKey.getBytes(StandardCharsets.UTF_8);
    }

    // 토큰 복호화 로직
    public void checkToken(String access, String refresh, String type) {
        UserVO user = new UserVO();
        try {
                if(type.equals(JwtUtils.ACCESS_KEY.name())){
                    System.out.println("검증 access==========="+JwtUtils.ACCESS_KEY.privateKey);
                    Jwts.parser().setSigningKey(this.generateKey(JwtUtils.ACCESS_KEY.privateKey)).parseClaimsJws(access);
                } else {
                    Claims sep = Jwts.parser().setSigningKey(this.generateKey(JwtUtils.REFRESH_KEY.privateKey)).parseClaimsJws(refresh).getBody();
                    user.setUserId((String) sep.get("user"));
                }
        }catch (SignatureException e){
            this.check = false;
            this.errMsg.put("403",type+" 비밀키가 일치하지 않음");
        }catch (MalformedJwtException e) {
            this.check = false;
            this.errMsg.put("405",type+" 토큰이 손상됨");
        } catch (ExpiredJwtException e) {
            // refresh 토큰도 기간 만료시 return 시켜 재귀함수 탈출
            if(type.equals(JwtUtils.REFRESH_KEY.name())){
                this.check = false;
                this.errMsg.put("408",type+" 유효기간이 만료됨");
                return;
            }
            // access 토큰이 만료되었을 경우 재귀함수로 refresh 토큰 검사 호출
            this.checkToken("",refresh,JwtUtils.REFRESH_KEY.name());
        } catch (UnsupportedJwtException e) {
            this.check = false;
            this.errMsg.put("404",type+" 지원하지 않음");
        } catch (IllegalArgumentException e) {
            this.check = false;
            this.errMsg.put("401",type+" 암호화 정보 없음");
        } catch (JwtException e){
            this.check = false;
            this.errMsg.put("500",type+" 기타 에러");
        }
        if(type.equals(JwtUtils.REFRESH_KEY.name())){
            this.createAccessToken(user);
        }
    }


    private String createToken(UserVO user,String type) {
        long curTime = System.currentTimeMillis();
        String key = "";
        Long time = 0L;
        switch (type){
            case "ACCESS_KEY": key = JwtUtils.ACCESS_KEY.privateKey; time = JwtUtils.ACCESS_KEY.time;
            break;
            case "REFRESH_KEY": key = JwtUtils.REFRESH_KEY.privateKey; time = JwtUtils.REFRESH_KEY.time;
            break;
        }
        return Jwts.builder()
                .setSubject("Test JWT")

                //[2]
                //setHeaderParam 메소드를 통해 JWT 헤더가 지닐 정보들을 담는다.
                //alg 의 경우는 default 값이 SHA256이므로 따로 설정할 필요는 없다.
                //typ 를 셋팅 안해주면 오류 발생한다.
                .setHeaderParam("typ", "JWT")

                //[3] 만료 시간
                .setExpiration(new Date(curTime + time))

                //[4] 발급 시간
                .setIssuedAt(new Date(curTime))

                //[5] Payload 에 Private Claims 를 담기 위해 claim 메소드를 이용한다.
                // private claim으로 VO객체를 추가할 수 있음
                .claim(DATA_KEY, user.getUserId())

                //[6] 복호화 할때 사용하는 시그니처 설정.
                // header의 인코딩값 + payload의 인코딩값 + 비밀키 = 시그니처
                // signWith api는 해싱알고리즘과 비밀키가 필요하다.
                .signWith(SignatureAlgorithm.HS256, this.generateKey(key))

                //생성
                .compact();
    }


    // 랜덤 개인키 생성기(200자)
    private static String randomKey(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 200;
        Random random = new Random();

        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }
}