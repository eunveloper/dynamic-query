# Description
Spring Data JPA Querydsl 라이브러리를 사용하다보면 Entity마다 Repository를 생성해주고 필요에 따라 Query를 매번 작성해줘야 합니다.  
또한 Entity 기반이 아닌 DTO 기반 조회 방식으로 구조가 수정될 때마다 DTO 수에 비례한 코드 개발 비용이 생기게 됩니다.  
이에 해당 프로젝트는 위와같은 번거로움을 해소하기 위해 개발되었으며 각종 SQL 구문에 맞는 Parameter와 Entity, DTO 기반으로 개발자가 직접 작성해야 하는 공통 행위들을 대신 구성해주는 라이브러리로  
사용자는 필요한 Query 방식에 맞춰 적절한 메소드를 활용하면 그에 맞는 Query가 구성되어 리턴되는 사용방식으로 단순하고 간편하게 Dynamic한 Query를 생성해줍니다.

<br/>

# Function Release
- 2022.06.16

  - 단일 WHERE 절 구현 (equals / getter)

- 2022.06.15

  - 단일 SELECT 절 구현
  - 단일 FROM 절 구현


<br/>

# How To Use
- 2022.06.16
``` Java
@Autowired
private DynamicRepository dynamicRepository;

dynamicRepository.searchAllByConditions(Dto.class, qEntity, SearchCondition...);
```

``` Java
public class SearchCondition {

    private EntityPath path;
    private Object param;
    private Constant.Method method;

}
```
  - Query에 WHERE절을 추가하고싶은 경우, 조건에 맞는 SearchCondition을 필요한 갯수만큼 생성하여 파라미터로 담아줍니다.
  - SearchCondition을 구성하는 방식은 다음과 같습니다.
    - EntityPath : 검색 기준이 되는 Entity의 QClass
    - param : 검색 기준 값을 담는 객체 (Entity의 필드명과 동일해야 합니다.)
    - Method : 검색 행위 (오늘기준 equals, getter 지원)

- 2022.06.15
``` Java
@Autowired
private DynamicRepository dynamicRepository;

dynamicRepository.searchAllByConditions(Dto.class, qEntity, null);  // 3rd parameter is not supported
```
  - 조회하고자 하는 Entity의 필드명과 얻고자 하는 DTO의 필드명 기준으로 동일하게 사용하여야 합니다.
