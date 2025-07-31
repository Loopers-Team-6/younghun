package com.loopers.application.payment;

import static org.assertj.core.api.Assertions.assertThat;

import com.loopers.application.order.OrderCreateCommand;
import com.loopers.application.order.OrderCreateInfo;
import com.loopers.application.order.OrderFacade;
import com.loopers.application.order.OrderItemCommands;
import com.loopers.domain.point.PointModel;
import com.loopers.domain.point.PointRepository;
import com.loopers.domain.user.UserModel;
import com.loopers.domain.user.UserRepository;
import com.loopers.utils.DatabaseCleanUp;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/sql/test-fixture.sql")
class PaymentServiceIntegrationTest {

  @Autowired
  private PaymentFacade paymentFacade;

  @Autowired
  private OrderFacade orderFacade;

  @Autowired
  private PointRepository pointRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private DatabaseCleanUp databaseCleanUp;

  private OrderCreateInfo orderCreateInfo;
  private String userId;

  @BeforeEach
  void init() {
    List<OrderItemCommands> orderItemModels = new ArrayList<>();

    orderItemModels.add(new OrderItemCommands(
        1L, 1L
    ));
    userId = "userId";
    OrderCreateCommand command =
        new OrderCreateCommand(userId,
            "서울시 송파구"
            , orderItemModels, "메모..");
    orderCreateInfo = orderFacade.create(command);

    UserModel userModel = userRepository.save(
        UserModel.create()
            .userId(userId)
            .email("email@eami.com")
            .birthday("2022-01-01")
            .gender("M")
            .build()
    );
  }

  @AfterEach
  void tearDown() {
    databaseCleanUp.truncateAllTables();
  }

  @DisplayName("결제 생성 시, 포인트가 차감이 되어진다.")
  @Test
  void returnDecreasePoint_whenPaymentCreated() {
    //given
    String orderNumber = orderCreateInfo.orderNumber();
    pointRepository.charge(userId, 500000);
    PointModel afterPoint = pointRepository.get(userId);
    BigInteger totalPrice = BigInteger.valueOf(100000);
    PaymentCommand command = new PaymentCommand(userId, orderNumber, totalPrice, "shot");

    //when
    PaymentInfo payment = paymentFacade.payment(command);
    PointModel currentPoint = pointRepository.get(userId);
    //then
    assertThat(currentPoint.getPoint()).isEqualTo(
        BigInteger.valueOf(afterPoint.getPoint()
        ).subtract(totalPrice).intValue());

    System.out.println(payment);
  }
}
