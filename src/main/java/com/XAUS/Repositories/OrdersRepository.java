package com.XAUS.Repositories;
import com.XAUS.DTOS.OrderProductConvertResponseDTO;
import com.XAUS.DTOS.OrdersConvertResponseDTO;
import com.XAUS.Models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository já trás todos os métodos de acesso ao db sem ter que implementar nada, alem do tipo do repository que ela retorna
//E o tipo do id

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query(value = "select products->>'productName' as productName, products->>'productPrice' as productPrice , products->>'buyedQuantity' as buyedQuantity FROM (select jsonb_array_elements(o.products) as products from orders as o  WHERE o.id = :id )as subquery "

            , nativeQuery = true)
    public List<OrderProductConvertResponseDTO> findProductsBySomething (@Param("id") Long id);

    @Query(value = "SELECT o.id AS Id, o.user_id AS userId, o.user_name AS userName, " +
            " o.client_id as clientId, o.client_cpf as clientCpf, " +
            "o.client_name as clientName , o.order_price AS orderPrice, o.its_payed as itsPayed " +
            "FROM orders AS o " +
            "WHERE (:param_type = 'user' AND o.user_id = :id) OR (:param_type = 'client' AND o.client_id = :id)"+
            "OR (:param_type = 'order' AND o.id = :id)", nativeQuery = true)
    public List<OrdersConvertResponseDTO> findBySomething(@Param("id") Long id, @Param("param_type") String paramType);


}
