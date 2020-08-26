package restapi.demo;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Daeho Oh
 * @since 2020-08-17
 */
// 추가적인 설정이 필요하지 않으면 애노테이션이 없어도 됨
// spring-boot-starter-data-rest 의존성 주입 시 기본적으로 api 생성함
// @RepositoryRestResource
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
