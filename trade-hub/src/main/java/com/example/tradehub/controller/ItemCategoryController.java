package com.example.tradehub.controller;

import com.example.tradehub.entity.dto.ItemCategoryDTO;
import com.example.tradehub.entity.pojo.ItemCategory;
import com.example.tradehub.result.PageResult;
import com.example.tradehub.result.Result;
import com.example.tradehub.service.ItemCategoryService;
import com.example.tradehub.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;

@Tag(name = "商品分类管理")
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @GetMapping("/list")
    @Operation(summary = "商品分类列表")
    public Result<PageResult<ItemCategory>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name){
        return Result.success(itemCategoryService.pageQuery(page, pageSize, name));
    }

    @PostMapping("/add")
    @Operation(summary = "添加商品分类")
    public Result add(@RequestBody ItemCategoryDTO itemCategoryDTO){
        itemCategoryService.addCategory(itemCategoryDTO);
        return Result.success();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "修改商品分类")
    public Result updateCategory(@PathVariable Long id, @RequestBody ItemCategoryDTO itemCategoryDTO){
        //无论前端在 DTO 里传了什么 id，甚至传了 null，后端都会强制把它替换成路径里的 id。
        itemCategoryDTO.setId(id);
        itemCategoryService.updateCategory(itemCategoryDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除商品分类")
    public Result deleteCategoryById(@PathVariable Long id){
        itemCategoryService.deleteCategoryById(id);
        return Result.success();
    }
}
