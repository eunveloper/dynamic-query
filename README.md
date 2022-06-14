# Description
Spring Data JPA Querydsl 라이브러리를 사용하다보면 Entity마다 Repository를 생성해주고 필요에 따라 Query를 매번 작성해줘야 합니다.  
또한 Entity 기반이 아닌 DTO 기반 조회 방식으로 구조가 수정될 때마다 DTO 수에 비례한 코드 비용이 생기게 됩니다.  
이에 해당 프로젝트는 그런 번거로움을 해소하기 위해 나온 프로젝트로 각종 Query의 절에 맞는 Parameter와 Entity, DTO 기반으로 개발자가 직접 작성해야 하는 공통행위들을 구현한 라이브러리로  
사용자는 필요한 Query 방식에 맞춰 적절한 메소드를 활용하면 방식에 맞는 Query가 구성되어 리턴되는 사용방식으로 단순하고 간편하게 다이나믹한 Query를 생성해줍니다.

<br/>

# Function Release
- 2022.06.15

  -   단일 SELECT 절 구현
  -   단일 FROM 절 구현

<br/>

# How To Use
- 2022.06.15
``` Java
@Autowired
private DynamicRepository dynamicRepository;

dynamicRepository.searchAllByConditions(Dto.class, qEntity, null);  // 3rd parameter is not supported
```
