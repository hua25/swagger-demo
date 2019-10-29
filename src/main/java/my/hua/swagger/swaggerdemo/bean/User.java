/**
 * FileName: User
 * Author:   liuzhenhua
 * Date:     2019/10/27 00:11
 * Description: 用户实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package my.hua.swagger.swaggerdemo.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类 <br>
 *
 * @author hua
 * @create 2019/10/27
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户", description = "用户实体")
public class User {

    @ApiModelProperty(value = "用户id", dataType = "Integer", example = "123")
    private Integer id;

    @ApiModelProperty(name = "userName", value = "姓名", dataType = "String", notes = "用户姓名")
    private String userName;
}
