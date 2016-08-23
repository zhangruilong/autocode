<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="GoodsMapper">
	
	
	<!-- 新增-->
	<insert id="save" parameterType="pd">
		insert into "TB_GOODS"(
			"GOODSID",	
			"GOODSCODE",	
			"GOODSNAME",	
			"GOODS_ID"
		) values (
			#{GOODSID},	
			#{GOODSCODE},	
			#{GOODSNAME},	
			#{GOODS_ID}
		)
	</insert>
	
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from "TB_GOODS"
		where 
			"GOODS_ID" = #{GOODS_ID}
	</delete>
	
	
	<!-- 修改 -->
	<update id="edit" parameterType="pd">
		update  "TB_GOODS"
			set 
				"GOODSID" = #{GOODSID},	
				"GOODSCODE" = #{GOODSCODE},	
				"GOODSNAME" = #{GOODSNAME},	
			"GOODS_ID" = "GOODS_ID"
			where 
				"GOODS_ID" = #{GOODS_ID}
	</update>
	
	
	<!-- 通过ID获取数据 -->
	<select id="findById" parameterType="pd" resultType="pd">
		select 
			"GOODSID",	
			"GOODSCODE",	
			"GOODSNAME",	
			"GOODS_ID"
		from 
			"TB_GOODS"
		where 
			"GOODS_ID" = #{GOODS_ID}
	</select>
	
	
	<!-- 列表 -->
	<select id="datalistPage" parameterType="page" resultType="pd">
		select
				a."GOODSID",	
				a."GOODSCODE",	
				a."GOODSNAME",	
				a."GOODS_ID"
		from 
				"TB_GOODS" a
	</select>
	
	<!-- 列表(全部) -->
	<select id="listAll" parameterType="pd" resultType="pd">
		select
				a."GOODSID",	
				a."GOODSCODE",	
				a."GOODSNAME",	
				a."GOODS_ID"
		from 
				"TB_GOODS" a
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from "TB_GOODS"
		where 
			"GOODS_ID" in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 #{item}
		</foreach>
	</delete>
	
</mapper>