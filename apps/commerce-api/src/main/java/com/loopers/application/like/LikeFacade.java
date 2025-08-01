package com.loopers.application.like;

import com.loopers.domain.like.LikeModel;
import com.loopers.domain.like.LikeRepository;
import com.loopers.domain.like.count.ProductSignalCountModel;
import com.loopers.domain.like.count.ProductSignalCountRepository;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeFacade {
  private final ProductSignalCountRepository productSignalCountRepository;
  private final LikeRepository likeRepository;

  @Transactional
  public void like(String userId, Long productId) {
    LikeModel likeModel = likeRepository.find(userId, productId).orElse(
        LikeModel.register(userId, productId)
    );
    //증가
    ProductSignalCountModel productSignalCountModel = productSignalCountRepository.find(productId).orElse(
        ProductSignalCountModel.register(productId)
    );
    productSignalCountRepository.save(productSignalCountModel);
    productSignalCountModel.increase(likeModel.isLiked());
    likeModel.like();
    likeRepository.save(likeModel);
  }

  @Transactional
  public void unlike(String userId, Long productId) {
    LikeModel likeModel = likeRepository.find(userId, productId).orElseThrow(
        () -> new CoreException(ErrorType.CONFLICT, "상품에 좋아요를 누르지 않는 경우, 좋아요 해제를 할 수 없습니다.")
    );
    //감소
    ProductSignalCountModel productSignalCountModel = productSignalCountRepository.find(productId).orElseThrow(
        () -> new CoreException(ErrorType.CONFLICT, "상품에 좋아요를 누르지 않는 경우, 좋아요 감소를 할 수 없습니다.")
    );
    likeRepository.save(likeModel);
    productSignalCountModel.decrease(likeModel.isLiked());
    likeModel.unLike();
    productSignalCountRepository.save(productSignalCountModel);
  }
}
