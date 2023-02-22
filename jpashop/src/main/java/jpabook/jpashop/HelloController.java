package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello") //hello 라는 url로 오면 이 컨트롤러가 호출되겠다
    public String hello(Model model){ //model에 데이터를 심어서 컨트롤러에서 view로 데이터를 넘길 수 있음
        model.addAttribute("data", "hello~!~!");
        return "hello"; // return은 화면 이름. 결과가 화면 렌더링시 resources/tamplates/hello.html로 이동이 됨
        //스프링부트 타임리프가 viewName을 자동으로 매핑해버림
    }

}
