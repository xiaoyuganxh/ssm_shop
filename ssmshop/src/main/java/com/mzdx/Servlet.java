package com.mzdx;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

           /* @Autowired
            private AdminService adminService;
            @RequestMapping(value = "/uploadHeadImage",method = RequestMethod.POST)
            public String uploadHeadImage(

            ) {

                try {

                    System.out.println("==========Start=============");
                    String realPath = request.getSession().getServletContext().getRealPath("/");
                    String resourcePath = "resources/uploadImages/";
                    if(imageFile!=null){
                        if(FileUploadUtil.allowUpload(imageFile.getContentType())){
                            String fileName = FileUploadUtil.rename(imageFile.getOriginalFilename());
                            int end = fileName.lastIndexOf(".");
                            String saveName = fileName.substring(0,end);
                            File dir = new File(realPath + resourcePath);
                            if(!dir.exists()){
                                dir.mkdirs();
                            }
                            File file = new File(dir,saveName+"_src.jpg");
                            imageFile.transferTo(file);
                            String srcImagePath = realPath + resourcePath + saveName;
                            int imageX = Integer.parseInt(x);
                            int imageY = Integer.parseInt(y);
                            int imageH = Integer.parseInt(h);
                            int imageW = Integer.parseInt(w);
                            //这里开始截取操作
                            System.out.println("==========imageCutStart=============");
                            String imgCut = ImgCut.imgCut(srcImagePath,imageX,imageY,imageW,imageH);
                            System.out.println("==========imageCutEnd============="+imgCut);
                            Map<String, String> map=new HashMap<String, String>();
                            map.put("id", id);
                            map.put("photo", imgCut);
                            int updateId = adminService.updatePhoto(map);
                            if(updateId>0){
                                Admin admin = adminService.selectByUid(updateId);
                                //将用户信息存入session
                                request.getSession().removeAttribute(WebKeys.ADMINUSER_KEY);
                                request.getSession().setAttribute(WebKeys.ADMINUSER_KEY, admin);
                            }
                        }
                    }


                } catch (Exception e) {

                }

                return "common/changePhoto";
            }

        }*/

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
