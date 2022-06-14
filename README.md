# Description
Spring Data JPA Querydsl 라이브러리를 사용할때, Entity마다 Repository와 Query를 매번 생성하고 및 구성하는 번거로움을 해소하기 위해 나온 프로젝트로 각종 Query의 절에 맞는 Parameter, Entity, DTO 기반의 사용방식으로 단순하고 간편하게 다이나믹한 Query를 생성해주는 Dynamic Querydsl 라이브러리.  

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
