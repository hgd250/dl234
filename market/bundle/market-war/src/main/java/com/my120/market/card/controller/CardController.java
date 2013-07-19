package com.my120.market.card.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.my120.market.card.bo.ICardBO;
import com.my120.market.card.bo.ICardTypeBO;
import com.my120.market.model.CardQueryModel;
import com.my120.market.model.CardTypeModel;
import com.my120.market.utils.ViewExcelPOI;

/**
 * 卡管理Controller
 * 
 * @author cai.yc
 * 
 *         2012-11-14
 */
@Controller
public class CardController implements ServletContextAware {

    @Autowired
    private ICardBO cardBO;

    @Autowired
    private ICardTypeBO cardTypeBO;

    private ServletContext context;

    /**
     * 发卡输入页
     * 
     * @author cai.yc 2012-11-20
     * @param model
     * @return
     */
    @RequestMapping("/toCreateCard.html")
    public String toCreateCardAction(ModelMap model) {
        List<CardTypeModel> cardTypeList = cardTypeBO.listAllCardType();
        model.put("cardTypeList", cardTypeList);
        return "card/to_create_card.html";
    }

    /**
     * 发卡
     * 
     * @author cai.yc 2012-11-20
     * @param model
     * @param query
     *            query.createCount : 生成数量,为必填项目,最大不能超过50000<br>
     *            query.cityCode : 六位城市代码,如201000<br>
     *            query.typeCode : 四位类型服务级别代码,如0102表示类型为01,服务级别为02<br>
     * @param req
     * @param res
     * @return 返回"c"含义：<br>
     *         "A0000" : 成功<br>
     *         "A0001" : 卡号剩余数量不够<br>
     *         "A0002" : 输入的发卡数量不合法可能为0,或超过500000<br>
     * 
     */
    @RequestMapping("/doCreateCard.html")
    public String doCreateCardAction(ModelMap model, CardQueryModel query, HttpServletRequest req, HttpServletResponse res) {
        int leftCount = cardBO.cardIdLeft(query);
        if (query.getCreateCount() == null) {
            model.put("c", "A0003");
        } else if (query.getCreateCount() <= 0 || query.getCreateCount() > 500000) {
            // 发卡数量不合法
            model.put("c", "A0002");
        } else if (leftCount < query.getCreateCount()) {
            // 剩余数量不足
            model.put("c", "A0001");
            model.put("d", leftCount);
        } else {
            // 参数合法,生成卡、激活码
            query.setCreateIp(req.getRemoteHost());
            query.setCreateUserId((String) req.getSession().getAttribute("MY120_MARKET_LOGIN_USER_INFO"));
            int result = cardBO.createCard(query);
            model.put("timestamp", query.getGmtCreate());
            model.put("cardTypeName", cardTypeBO.getCardTypeName(query.getTypeCode()));
            model.put("d", result);
            model.put("c", "A0000");
        }
        return "card/do_create_card.html";
    }

    /**
     * 下载卡号,激活码 excel文件(根据发卡时间gmt_create)
     * 
     * @author cai.yc 2012-11-20
     * @param model
     * @param query
     *            query.gmtCreate : 发卡时间,为必填参数
     * @return 指定发卡时间的所有卡列表的excel文件,默认文件名为日期
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/downloadCard.html")
    public ModelAndView doExcelAction(ModelMap model, CardQueryModel query) throws Exception {
        query.setPageindex("0");
        query.setPagesize("20");
        String[] columnKey = { "number", "cdkey" };
        String[] columnValue = { "卡号", "激活码" };
        Map<String, Object> result = cardBO.selectCardInfoList(query);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("data");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ViewExcelPOI viewExcel = new ViewExcelPOI(columnKey, columnValue, dataList, format.format(new Date()));
        return new ModelAndView(viewExcel);
    }

    /**
     * 分页查询卡信息集合
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:12:02
     * 
     */
    @RequestMapping("/doSearchCardInfoList.html")
    public ModelAndView doSearchCardInfoListAction(ModelMap model, CardQueryModel query) {
        ModelAndView jsonView = new ModelAndView("libJsonView");
        query.setIsLimit("1");
        try {
            Map<String, Object> result = cardBO.selectCardInfoList(query);
            JSONObject obj = new JSONObject();
            obj.put("sumcount", result.get("sumcount"));
            obj.put("count", result.get("count"));
            obj.put("data", result.get("data"));
            jsonView.addObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonView;
    }

    /**
     * 健康卡管理页面初始化
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:12:24
     * 
     */
    @RequestMapping("/toSearchCardInfoList.html")
    public String toSearchCardInfoListAction(ModelMap model, CardQueryModel query) {
        List<CardTypeModel> cardTypeList = cardTypeBO.listAllCardType();
        model.put("cardTypeList", cardTypeList);
        return "card/to_search_card.html";
    }

    /**
     * 导出excel
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:13:01
     * 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/doExcel.html")
    public ModelAndView doExcelForCreateAction(ModelMap model, CardQueryModel query) throws Exception {
        query.setPageindex("0");
        query.setPagesize("20");
        String[] columnKey = { "number", "cdkey", "cardTypeName", "cardPrice", "cityName", "status", "gmtCreate",
                "createIp" };
        String[] columnValue = { "卡号", "激活码", "类型", "价格", "发行城市", "使用状态", "发卡时间", "IP地址" };
        Map<String, Object> result = cardBO.selectCardInfoList(query);
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) result.get("data");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ViewExcelPOI viewExcel = new ViewExcelPOI(columnKey, columnValue, dataList, format.format(new Date()));
        return new ModelAndView(viewExcel);
    }

    /**
     * 卡详细信息查看
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:13:21
     * 
     */
    @RequestMapping("/doCardInfo.html")
    public String doCardInfoAction(ModelMap model, CardQueryModel query) {
        Map<String, Object> result = cardBO.getCardInfoByCardNumber(query);
        model.put("data", result.get("data"));
        return "card/do_card_info.html";
    }

    /**
     * 注销健康卡首页
     * 
     * @author 韩友军<hanyoujun@gmail.com>
     * @since 2012-11-20下午12:56:41
     * 
     */
    @RequestMapping("/doCancelCard.html")
    public String doCancelCardAction() {
        return "card/do_cancel_card.html";
    }

    /**
     * 注销健康卡提交页
     * 
     * @param model
     * @param mfile
     * @author 韩友军<hanyoujun@gmail.com>
     * @since 2012-11-20下午12:57:08
     * 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/doCancelCardByExcel.html", method = RequestMethod.POST)
    public String doCancelCardByExcelAction(ModelMap model, HttpServletRequest request) {

        DiskFileItemFactory objDiskFileItemFactory = new DiskFileItemFactory();
        objDiskFileItemFactory.setSizeThreshold(1024 * 1024);// 缓存
        ServletFileUpload objServletFileUpload = new ServletFileUpload(objDiskFileItemFactory);

        try {
            List<FileItem> fileItems = objServletFileUpload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                if (!fileItem.isFormField()) {
                    String strPath = this.context.getRealPath("/upload/");
                    File file = new File(strPath + new Date().getTime() + ".xls");
                    fileItem.write(file);
                    String strFilePath = file.getPath();
                    Map<String, Object> mapData = cardBO.cancelCard(strFilePath);
                    model.put("data", mapData);
                    model.put("success", true);
                }
            }
        } catch (Exception e) {
            model.put("success", false);
        }
        return "card/do_cancel_card_by_excel.html";
    }

    public ICardBO getCardBO() {
        return cardBO;
    }

    public void setCardBO(ICardBO cardBO) {
        this.cardBO = cardBO;
    }

    public ICardTypeBO getCardTypeBO() {
        return cardTypeBO;
    }

    public void setCardTypeBO(ICardTypeBO cardTypeBO) {
        this.cardTypeBO = cardTypeBO;
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

}
