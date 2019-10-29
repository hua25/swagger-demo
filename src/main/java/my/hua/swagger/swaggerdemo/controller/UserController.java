/**
 * FileName: UserController
 * Author:   hua
 * Date:     2019/10/20 22:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package my.hua.swagger.swaggerdemo.controller;

import io.swagger.annotations.*;
import my.hua.swagger.swaggerdemo.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hua
 * @create 2019/10/20
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", tags = {"用户操作类"})
public class UserController {

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询用户",
            notes = "通过id查询用户信息，参数为int类型",
            response = User.class,
            tags = {"查询"})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied",
                    responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
            @ApiResponse(code = 404, message = "User not found") })
    public User selectUserById(@ApiParam(name = "id",value = "用户id",required = true,example = "123") @PathVariable(value = "id", required = true) int id) {
        return new User(id, "Tom");
    }
}
