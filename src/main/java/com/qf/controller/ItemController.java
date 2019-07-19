package com.qf.controller;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.utils.PageInfo;
import com.qf.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qf.constant.SsmConstant.REDIRECT;

/**
 * @program: three
 * @author: 羊角
 * @create: 2019-07-16 21:32
 **/

@Controller
@RequestMapping("/item")
public class ItemController {


    @Autowired
    private ItemService itemService;
    //商品的首页
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "size",defaultValue = "5")Integer size,
                       Model model
                       ){
        //1.调用service查找数据
        PageInfo<Item> pageInfo =  itemService.findItemAndLimit(name,page,size);
        //2.将信息放到request域中
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);
        //转发页面
        return "/item/item_list";
    }



    //添加商品

//    Request URL: http://localhost/item/add-ui
//    Request Method: GET
    @GetMapping("/add-ui")
    public String addUI(){
        return "item/item_add";
    }

    //添加商品
//    Request URL: http://localhost/item/add
//    Request Method: POST
        @Value("${pic_types}")
        private String picType;


    @PostMapping("/add")
    public String add(@Valid Item item,
                      BindingResult bindingResult,
                      MultipartFile picFile,
                      HttpServletRequest request,
                      RedirectAttributes redirectAttributes) throws IOException {

        //======================================校验参数============================

        //=========================================================================

        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            // 参数不合法
            redirectAttributes.addAttribute("addInfo",msg);
            return REDIRECT + "/item/add-ui";
        }

        //1.对图片的大小做校验 要求图片小于5M  5242880
        long size = picFile.getSize();
        if (size>5242880){
            //   图片过大
            redirectAttributes.addAttribute("addInfo","图片大小超过5M");
            return REDIRECT + "/item/add-ui";
        }
        //2.对图片的类型做校验   jpg, png, gif
        boolean flag = false;
        String[] types = picType.split(",");
        for (String type : types) {
            if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(),type)){
                //图片类型正确
                flag = true;
                break;
            }
        }
        
        if(!flag){
            //   图片类型不正确
            redirectAttributes.addAttribute("addInfo","图片格式不正确");
            return REDIRECT + "/item/add-ui";
        }
        
        //3.校验图片是否损坏
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image == null){
            // 图片已经损坏
            redirectAttributes.addAttribute("addInfo","图片已经损坏");
            return REDIRECT + "/item/add-ui";
        }

        //========================================将图片保存到本地=======================================================
        //转移,转换,完成图片的上传
        //1.给图片起一个新名字
        String prefix = UUID.randomUUID().toString();
        String suffix = org.apache.commons.lang3.StringUtils.substringAfterLast(picFile.getOriginalFilename(),".");
        String newName = prefix+"."+suffix;
        //2.将图片保存到本地
        String path = request.getServletContext().getRealPath("") + "\\static\\img\\" + newName;
        File file = new File(path);
        picFile.transferTo(file);

        //3.封装图片的访问路径
        String pic ="http://localhost/static/img/" + newName;
        //=====================================图片校验=============================================



        //=====================================保存商品数据================================================
        item.setPic(pic);

        //调用service保存商品信息

        Integer count = itemService.save(item);

        //判断count
        if (count==1){
            return REDIRECT + "/item/list";
        }else{
            // 添加用户信息失败
            redirectAttributes.addAttribute("addInfo","添加用户失败");
            return REDIRECT + "/item/add-ui";
        }


    }




//根据用户id删除商品
//Request URL: http://localhost/item/del/13
//    Request Method: DELETE
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVo del(@PathVariable Long id){
        //1.调用删除商品信息
        Integer count = itemService.delete(id);
        //根据返回信息来给页面响应信息
        if (count==1){
            return new ResultVo(0,"删除商品成功",null);
        }else{
            return new ResultVo(1,"删除商品失败",null);
        }


    }






    //修改商品
//    Request URL: http://localhost/item/update-ui
//    Request Method: GET
        @GetMapping("/update-ui")
        public String update(@RequestParam Long id,
                             Model model){
            //调用service查询该商品
            Item item = itemService.findById(id);

            if (item!=null) {
                //将查询信息放在域中
                model.addAttribute("item", item);
                return  "item/item_update";
            }else{
                model.addAttribute("msg","商品不存在");
                return  "/item/item_list";
            }

            }

        //存入商品
//        Request URL: http://localhost/item/update
//    Request Method: POST



        @PostMapping("/update")
        public String update (@Valid Item item,
                              BindingResult bindingResult,
                              MultipartFile picFile,
                              HttpServletRequest request,
                              RedirectAttributes redirectAttributes) throws IOException {



            //校验参数是否合法
            if (bindingResult.hasErrors()){
                String msg = bindingResult.getFieldError().getDefaultMessage();
                redirectAttributes.addAttribute("updateInfo", msg);
                return REDIRECT+"/item/update-ui?id="+item.getId();
            }
            long size = picFile.getSize();

            if (size!=0){

                //校验图片大小
                if ( size> 5242880) {
                    redirectAttributes.addAttribute("updateInfo", "图片的大小超过5M");
                    return REDIRECT + "/item/update-ui?id=" + item.getId();
                }
                //校验图片格式是否正确
                boolean flag = false;
                String[] type = picType.split(",");
                for (String s : type) {
                    if (StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), s)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    redirectAttributes.addAttribute("updateInfo", "图片格式不正确");
                    return REDIRECT + "/item/update-ui?id=" + item.getId();
                }

                //校验图片是否损坏
                BufferedImage image = ImageIO.read(picFile.getInputStream());
                if (image == null) {
                    redirectAttributes.addAttribute("updateInfo", "图片已经损坏");
                    return REDIRECT + "/item/update-ui?id=" + item.getId();
                }


            //===================================将图片保存到本地===========================================================
            String str = UUID.randomUUID().toString();
            String suffix = org.apache.commons.lang3.StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
            String newName = str+"."+suffix;

            String newPath = request.getServletContext().getRealPath("")+"/static/img/"+newName;
            File file = new File(newPath);
            picFile.transferTo(file);

                String pic ="http://localhost/static/img/" + newName;
                item.setPic(pic);
            }
            //==================================存入数据库====================================================
            //都合格后存入数据库
            //调用service层存入信息
            Integer count = itemService.update(item);
            System.out.println(item);
            if (count==1){

                return REDIRECT + "/item/list";
            }else{
                redirectAttributes.addAttribute("updateInfo", "商品信息存入失败");
                return REDIRECT+"/item/update-ui?id="+item.getId();
            }

        }
















}
