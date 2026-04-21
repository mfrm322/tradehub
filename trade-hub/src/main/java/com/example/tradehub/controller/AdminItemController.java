package com.example.tradehub.controller;



import com.example.tradehub.entity.pojo.Item;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.result.Result;
import com.example.tradehub.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "管理员对商品操作模块")
@RestController
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class AdminItemController {

    private final ItemService itemService;

    @PutMapping("/on-shelf/{id}")
    @Operation(summary = "管理员审核商品通过并上架")
    public Result addItem(@PathVariable Long id){
        itemService.onShelf(id);
        return Result.success();
    }

    @PutMapping("/off-shelf/{id}")
    @Operation(summary = "管理员下架该商品")
    public Result offSelf(@PathVariable Long id){
        itemService.offShelf(id);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询商品列表")
    public Result<PageResult<Item>> pageQuery(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long categoryId){
        return Result.success(itemService.pageQuery(page, pageSize, title, userId, status,categoryId));
    }
}
