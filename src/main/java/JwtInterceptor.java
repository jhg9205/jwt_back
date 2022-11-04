import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tbcom.jwt.utils.JwtService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;


@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JwtService jwtService = new JwtService();
        if(request.getRequestURI().contains("/login")) { // login 호출 시 무조건 통과
            return true;
        } else {
            String access_token = request.getHeader("access"); // access 토큰 전달받기
            String refresh_token = request.getHeader("refresh"); // refresh 토큰 전달받기
            // access 토큰이 있을 경우 유효성 검사 실시
            if(access_token != null && access_token.length() > 0) {
                jwtService.checkToken(access_token, refresh_token, JwtService.JwtUtils.ACCESS_KEY.name()); // 토큰 유효성 검증
                // access 토큰이 정상일 경우
                if(jwtService.check){
                    // access 토큰이 정상이지만 기간이 만료되었을 경우 생성된 access 토큰을 저장
                    if(!jwtService.access.equals("")){
                        response.setHeader("access_token", jwtService.access);
                    }
                }else { // 예외 발생 시 false 를 넘겨 login 페이지로 이동
                    JSONObject obj = new JSONObject();
                    obj.put("errCode",jwtService.errMsg.keySet().toString().substring(1,4).trim());
                    obj.put("errMsg",jwtService.errMsg.get(obj.get("errCode")));

                    PrintWriter param = response.getWriter();
                    param.write(obj.toString());
                    response.setHeader("err", "false");
                }
            }
            // 검증 결과에 따른 Controller 호출
            return jwtService.check;
        }
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
