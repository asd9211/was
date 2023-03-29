# was

### 순수 Java를 통해 WAS를 구현한 예제입니다.
### Host 헤더 해석
- HttpConnectionRequest 클래스에서 Host를 해석하여 저장합니다.
- VirtualHost 클래스에서 Host에 매핑되는 정보를 불러옵니다.

### 설정파일
- application.json 파일로 connection-pool-size, port, virtual-host 정보를 관리합니다.

### 에러 핸들링
- HttpConnectionResponse 클래스의 responseErrorPage 메서드를 통해 원하는 HttpStatus의 상태의 오류를 반환해줍니다.
- VirtualHost와 매핑되는 error 파일을 출력합니다. 없을 경우 default error 파일을 출력합니다.

### 보안규칙
- SecurityFilter 클래스를 통해 상위 디렉터리와 exe파일 요청시 403 페이지를 출력합니다.
- SecurityFilter 클래스를 통해 매핑되는 정보가 없을 시 404 페이지를 출력합니다.

### Log 정책
- logback.xml 파일 설정을 통해 하루 단위로 로그를 남깁니다.
- log.error를 통해 오류 발생 시, StackTrace 전체를 로그 파일에 남깁니다.
- 단순 정보성 log시 log.info를 통해 게시합니다.

### WAS 구현
- MappingRule 클래스를 통해 URL과 응답을 담당하는 객체를 매핑합니다.
- RequestMapper 클래스를 통해 최초 로드시 SimpleServlet 구현체들의 인스턴스를 로드해놓습니다.
- 클라이언트 요청시 MappingRule에서 매핑되는 객체를 RequestMapper를 통해 로드하여 해당 Servlet구현체를 실행시킵니다.

### 테스트
- Junit을 통해 단위테스트를 진행합니다.
- RequestProcessorTest에서 실제 통합테스트가 이루어집니다.
