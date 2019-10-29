/**
 * FileName: GoodsController
 * Author:   hua
 * Date:     2019/10/26 23:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package my.hua.swagger.swaggerdemo.controller;

import io.swagger.annotations.Api;
import my.hua.swagger.swaggerdemo.bean.Goods;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hua
 * @create 2019/10/26
 * @since 1.0.0
 */
@RestController
@RequestMapping("/goods")
@Api(tags = {"商品Controller"})
public class GoodsController {

    @GetMapping("/{id}")
    private Goods selectGoodsById(@PathVariable(value = "id", required = true) String id) {
        return Goods.builder().id(id).name("Goods-" + id).build();
    }

}
