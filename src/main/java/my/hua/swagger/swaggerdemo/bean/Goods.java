/**
 * FileName: Goods
 * Author:   hua
 * Date:     2019/10/26 23:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package my.hua.swagger.swaggerdemo.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品实体类
 *
 * @author hua
 * @create 2019/10/26
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods {

    private String id;
    private String name;

}
