package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Product;
import models.SubCategory;
import models.TopCategory;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Messages;
import ninja.params.Param;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.google.inject.Inject;

import conf.PosterConstants;
import filters.SessionCustomerExistFilter;

/**
 * Controller class, that provides the catalog functionality.
 * 
 * @author sebastianloob
 */
public class CatalogController
{

    @Inject
    Messages msg;

    @Inject
    PosterConstants xcpConf;

    /**
     * Returns a product detail page for the given product.
     * 
     * @param productId
     * @param context
     * @return
     */
    @FilterWith(SessionCustomerExistFilter.class)
    public Result productDetail(@Param("productId") int productId, Context context)
    {
        final Map<String, Object> data = new HashMap<String, Object>();
        WebShopController.setCommonData(data, context, xcpConf);
        // put product to data map
        data.put("productDetail", Product.getProductById(productId));
        return Results.html().render(data);
    }

    /**
     * Returns a product overview page for the given sub category.
     * 
     * @param subCategoryId
     * @param context
     * @return
     */
    @FilterWith(SessionCustomerExistFilter.class)
    public Result productOverview(@Param("categoryId") int subCategoryId, Context context)
    {
        final Map<String, Object> data = new HashMap<String, Object>();
        int pageSize = xcpConf.PRODUCTS_PER_PAGE;
        WebShopController.setCommonData(data, context, xcpConf);
        // add products of the given sub category to data map
        addSubCategoryProductsToMap(subCategoryId, 1, pageSize, data);
        // add sub category to data map
        data.put("category", SubCategory.getSubCategoryById(subCategoryId));
        return Results.html().render(data);
    }

    /**
     * Returns a product overview page for the given top category.
     * 
     * @param topCategoryId
     * @param context
     * @return
     */
    @FilterWith(SessionCustomerExistFilter.class)
    public Result topCategoryOverview(@Param("categoryId") int topCategoryId, Context context)
    {
        final Map<String, Object> data = new HashMap<String, Object>();
        int pageSize = xcpConf.PRODUCTS_PER_PAGE;
        WebShopController.setCommonData(data, context, xcpConf);
        // add products of the given top category to data map
        addTopCategoryProductsToMap(topCategoryId, 1, pageSize, data);
        // add top category to data map
        data.put("category", TopCategory.getTopCategoryById(topCategoryId));
        // return product overview page
        return Results.html().template(xcpConf.TEMPLATE_PRODUCT_OVERVIEW).render(data);
    }

    /**
     * Returns the products of the top category for the given page number as JSON.
     * 
     * @param topCategoryId
     * @param page
     * @return
     */
    public Result getProductOfTopCategory(@Param("categoryId") int topCategoryId, @Param("page") int page)
    {
        Result result = Results.json();
        final Map<String, Object> data = new HashMap<String, Object>();
        int pageSize = xcpConf.PRODUCTS_PER_PAGE;
        // add products of the given top category to data map
        addTopCategoryProductsToMap(topCategoryId, page, pageSize, data);
        return result.render(data);
    }

    /**
     * Returns the products of the sub category for the given page number as JSON.
     * 
     * @param pathname
     * @param page
     * @return
     */
    public Result getProductOfSubCategory(@Param("categoryId") int subCategoryId, @Param("page") int page)
    {
        Result result = Results.json();
        final Map<String, Object> data = new HashMap<String, Object>();
        int pageSize = xcpConf.PRODUCTS_PER_PAGE;
        // add products of the given sub category to data map
        addSubCategoryProductsToMap(subCategoryId, page, pageSize, data);
        return result.render(data);
    }

    /**
     * Adds products of the given top category to the given data map, according to the given page number.
     * 
     * @param topCategoryId
     * @param pageNumber
     * @param pageSize
     * @param data
     */
    private static void addTopCategoryProductsToMap(int topCategoryId, int pageNumber, int pageSize,
                                                    final Map<String, Object> data)
    {
        // get the given top category
        TopCategory category = TopCategory.getTopCategoryById(topCategoryId);
        // get the marked products, which should show in the top category
        PagingList<Product> pagingList = Ebean.find(Product.class).where().eq("topCategory", category)
                                              .eq("showInTopCategorie", true).findPagingList(pageSize);
        // add all products to the data map
        createPagingListProductOverview(pagingList, pageNumber, data);
    }

    /**
     * Adds products of the given sub category to the given data map, according to the given page number.
     * 
     * @param subCategoryId
     * @param pageNumber
     * @param pageSize
     * @param data
     */
    private static void addSubCategoryProductsToMap(int subCategoryId, int pageNumber, int pageSize,
                                                    final Map<String, Object> data)
    {
        // get the sub category by the given category
        SubCategory category = SubCategory.getSubCategoryById(subCategoryId);
        // get all products of the sub category
        PagingList<Product> pagingList = Ebean.find(Product.class).where().eq("subCategory", category)
                                              .findPagingList(pageSize);
        // add the products to the data map
        createPagingListProductOverview(pagingList, pageNumber, data);
    }

    /**
     * Adds the product from the given paging list to the data map.
     * 
     * @param pagingList
     * @param pageNumber
     * @param data
     */
    private static void createPagingListProductOverview(PagingList<Product> pagingList, int pageNumber,
                                                        final Map<String, Object> data)
    {
        // get row count in background
        pagingList.getFutureRowCount();
        // get the current page
        Page<Product> page = pagingList.getPage(pageNumber - 1);
        // get the products of the current page
        List<Product> list = page.getList();
        // remove some informations of the product list, to render a small-sized json-object
        for (int i = 0; i < list.size(); i++)
        {
            list.get(i).setAvailableSizes(null);
            list.get(i).setSubCategory(null);
            list.get(i).setTopCategory(null);
            list.get(i).setCart(null);
            list.get(i).setOrder(null);
        }
        // get the total page count
        int pageCount = pagingList.getTotalPageCount();
        // add the products to the data map
        data.put("products", list);
        // add the page count to the data map
        data.put("totalPages", pageCount);
        data.put("currentPage", pageNumber);
    }
}
