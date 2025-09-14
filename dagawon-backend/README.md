# DaGaWon-ROOT PROJECT

```Text
DaGaWon 개발 서버 정보

도메인

web
was
db

서버접속ID
서버접속pw
```

## DB 접속 정보

```Text
Host: 
Port: 
ID: 
PW: 
DB: 
```

## 서버 포트

| Server Name | PORT |
|-------------|------|
| DaGaWon-app    |  |
| DaGaWon-admin  |  |
| DaGaWon-batch  |  |

## 맥에서 포트 확인후 포트 강제 종료

```shell
sudo lsof -PiTCP -sTCP:LISTEN  
sudo kill -9 pid
```

## 윈도우에서 포트 확인후 강제 종료

```shell
netstat -ano 
taskkill /f /pid [PID]
```

## 레이어별 필수 어노테이션

```Java
 - Service   
   @Slf4j  
   @Service  
   @RequiredArgsConstructor  
 - vo & dto  
   @Getter
   @Setter  
   @ToString  
   @Builder  
   @AllArgsConstructor  
   @NoArgsConstructor  
 - Controller  
   @Tag(name = "01.사용자", description = "사용자 관리 API")  
   @Slf4j  
   @RestController  
   @RequiredArgsConstructor  
 - Entity  
   @Getter  
   @Setter  
   @Entity  
   @Table(name = "TB_CNTR_MASTER", schema = "anbu")  
```

## mariadb type -> java type

| mariadb 데이터타입 | 	java 데이터 타입                                 |
|:--------------|----------------------------------------------|
| BIT	          | Boolean                                      |
| TINYINT	      | Integer                                      |
| BOOL          | BOOLEAN [TINYINT(1)]	Integer                 |
| SMALLINT	     | Integer                                      |
| MEDIUMINT	    | Integer  / Unsigned면 Long                    |
| INT	Integer   | Unsigned면 Long                               |
| BIGINT	       | Long  Unsigned면 java.math.BigInteger         |
| FLOAT	        | FLOAT                                        |
| DOUBLE	       | Double                                       |
| DECIMAL	      | java.math.BigDecimal                         |
| DATE	         | java.time.LocalDate                          |
| DATETIME	     | java.time.LocalDateTime                      |
| TIMESTAMP	    | java.time.LocalDateTime                      |
| YEAR	         | Short / yearslsDateType이 설정됬다면 java.sql.Date |
| CHAR	         | 칼럼이 Binary 설정되있다면 String / 아니라면 byte[]       |
| VARCHAR	      | 칼럼이 Binary 설정되있다면 String / 아니라면 byte[]       |
| BINARY	       | byte[]                                       |
| VARBINARY	    | byte[]                                       |
| TINYBLOB	     | byte[]                                       |
| TINYTEXT	     | String                                       |
| BLOB	         | byte[]                                       |
| MEDIUMBLOB	   | byte[]                                       |
| LONGBLOB	     | byte[]                                       |
| TEXT	         | String                                       |
| MEDIUMTEXT	   | String                                       |
| LONGTEXT	     | String                                       |
| ENUM	         | String                                       |
| SET	          | String                                       |


```Text
로그레벨
trace(1단계): 가장 낮은 로그 레벨이며, debug보다 정보를 훨씬 상세하게 기록할 경우에 사용한다.
debug(2단계): 디버깅 목적으로 사용한다.
info(3단계): 주요 이벤트나 상태 등의 일반 정보를 출력할 목적으로 사용한다.
warn(4단계): 문제가 발생할 가능성이 있는 상태나 상황 등(비교적 작은 문제)에 관한 경고 정보를 출력할 목적으로 사용한다.
error(5단계): 심각한 문제나 예외 상황 등(비교적 큰 문제)에 대한 오류 정보를 출력할 목적으로 사용한다.
fatal(6단계): 가장 높은 로그 레벨이며, 프로그램 기능의 일부가 실패하거나 오류가 발생하는 등 아주 심각한 문제에 관한 정보를 출력할 목적으로 사용한다.
```

## PAGING 처리시 JPA 사용하지 않고 </br> Stream으로 처리하는 방법

```Java
ex) EscrowSvc > 600Line 정도에서 확인 가능합니다.
int pageSize = 10; // 해당하는 페이지에서 페이지당 row 수

Map<Integer, List<EscrowSvo.SearchEscrHistInfoListOutSvo>> collect = 
         IntStream.iterate(0, i -> i + pageSize)
        .limit((depListSvo.size() + pageSize - 1) / pageSize) // => depListSvo 해당하는 페이징 처리를 할 리스트 
        .boxed()
        .collect(Collectors.toMap(i -> i / pageSize, i 
        -> depListSvo.subList(i, Math.min(i + pageSize, depListSvo.size())))); // => depListSvo 해당하는 페이징 처리를 할 리스트
        
        // 위 내용에서 수정이 되어야 할 부분은
        // pageSize 와 depListSvo (해당 페이징 처리가 필요한 리스트)
        // 두가지의 항목이며, 나머지의 항목들은 고정을 시키면 된다.
        
int totalPage = collect.size()); // 전체 페이지 수

escrSvo.setHistList(collect.get(pageNum-1)); // 해당 하는 페이지를 get하는 방법
// mapdml 시작점은 0이기 때문에 0부터 꺼내와야한다.

int totalElements = depListSvo.size(); // 전체 엘리멘트 갯수
```
