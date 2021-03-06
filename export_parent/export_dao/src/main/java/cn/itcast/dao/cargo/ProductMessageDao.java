package cn.itcast.dao.cargo;


import cn.itcast.domain.cargo.ProductMessage;
import cn.itcast.domain.cargo.ProductMessageExample;

import java.util.List;

public interface ProductMessageDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String productNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    int insert(ProductMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    int insertSelective(ProductMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    List<ProductMessage> selectByExample(ProductMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    ProductMessage selectByPrimaryKey(String productNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProductMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_product_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProductMessage record);
}