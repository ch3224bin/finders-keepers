## 머니 뿌리기

### 문제해결 전략

#### 1. 토큰 발급

##### 고민되는 점

제가 문제를 잘 못 이해한 것 같기도 합니다.

고유한 3자리 문자열의 토큰을 발급한다. 고유함의 범위가 어디까지인지 고민스럽습니다.

##### 해결 방안

우선 아래의 단순한 방법으로 토큰을 발급하였습니다.

아래의 로직은 소수의 크기에 따라 생성되는 숫자가 매우 제한적이라서 단점이 많습니다.

대신 토큰 발급 방식을 확장 가능하게 설계하여 더 좋은 방법을 쉽게 도입할 수 있도록 하였습니다. 

1. Sequence 테이블에서 1부터 시퀀스 생성
2. 시퀀스와 소수(비밀키)를 곱함. (곱한 값을 계속 시퀀스라고 하겠습니다.)
3. 시퀀스를 6자리 숫자로 바꾼후 두번째 자리와 마지막 자리를 바꾸어 시퀀스를 유추하기 힘들게 함.
4. 시퀀스를 ascii 코드 33에서 126까지 중 uri safety한 값만 추려서 해당 진수로 변환.
5. 변환된 진수를 3자리로 leftpading 하여 완성합니다. 

#### 2. 뿌린돈 받기 동시성 처리

#### 해결 방안

JPA LockModeType.PESSIMISTIC_FORCE_INCREMENT lock을 사용하였습니다.

select for update 를 row 락을 거는 동시에, 수정시 version을 올려서

받기 경합시에 나중에 요청된 값으로 업데이트 되지 않도록 하였습니다.

#### 3. 변경되는 값은 config로 설정

토큰의 자리수, 토큰 만료 기간, 뿌린 정보 조회 기간은 config 파일로 관리하였습니다.

### API

#### 1. 머니 뿌리기

##### API 기본정보

| 메소드 | 요청 URL | 출력포멧 |
| :----- | :------- | :------- |
| POST   | /gift    | json     |

##### 요청변수

| 구분 | 요청 변수명 | 타입 | 설명 |
| :----- | :------- | :------- | :--- |
| Header | X-USER-ID | Number | 유저 ID |
| Header | X-ROOM-ID | String | 방 ID |
| BODY | amount | Number | 뿌릴 금액 |
| BODY | peoper | Number | 뿌릴 사람 수 |
  

##### 출력결과

| 필드 | 타입 | 설명 |
| :--- | :--- | :--- |
| token | String | 토큰값 |
| _links.self | Url | 토큰 정보 Url, 생성자만 조회 가능 |
| _links.gotcha | Url | 돈 가지기 Url |

```json
{
    "id": 1,
    "token": ",r7",
    "createdDate": "2020-06-27T14:33:00.4283421",
    "createUser": "1",
    "expireDate": "2020-06-27T14:43:00.4283421",
    "roomId": "1",
    "_links": {
        "self": {
            "href": "http://localhost:8080/gifts/,r7/receipt"
        },
        "gotcha": {
            "href": "http://localhost:8080/gifts/,r7"
        }
    }
}
```

##### 에러코드

| HTTP 코드 | 에러 코드 | 에러 메시지 |
| :--- | :--- | :--- |
| 401 | 401 | 인증 정보 없음 |
| 400 | 400 | 파라메터 정보 없음 |

#### 2. 받기

##### API 기본정보

| 메소드 | 요청 URL | 출력포멧 |
| :----- | :------- | :------- |
| GET   | /gift/{token}    | json     |

##### 요청변수

| 구분 | 요청 변수명 | 타입 | 설명 |
| :----- | :------- | :------- | :--- |
| Header | X-USER-ID | Number | 유저 ID |
| Header | X-ROOM-ID | String | 방 ID |
| PathVariable | token | String | 토큰값 |
  

##### 출력결과

| 필드 | 타입 | 설명 |
| :--- | :--- | :--- |
| amount | Number | 받은 금액 |
| keepers| String | 받은 사람 ID |

```json
{
    "amount": 3333,
    "keepers": "6"
}
```

##### 에러코드

| HTTP 코드 | 에러 코드 | 에러 메시지 |
| :--- | :--- | :--- |
| 401 | 401 | 인증 정보 없음 |
| 404 | TOKEN_ERROR_01 | 해당 토큰에 대한 정보를 찾을 수 없습니다. |
| 403 | GIFT_ERROR_01 | 뿌리기 당 한 사용자는 한번만 받을 수 있습니다. |
| 403 | GIFT_ERROR_02 | 자신이 뿌리기한 건은 자신이 받을 수 없습니다. |
| 403 | GIFT_ERROR_03 | 뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다. |
| 403 | GIFT_ERROR_04 | 뿌린 건은 10분간만 유효합니다. |
| 403 | GIFT_ERROR_05 | 뿌리기가 전부 소진되었습니다. |

#### 3. 조회

##### API 기본정보

| 메소드 | 요청 URL | 출력포멧 |
| :----- | :------- | :------- |
| GET   | /gift/{token}/receipt    | json     |

##### 요청변수

| 구분 | 요청 변수명 | 타입 | 설명 |
| :----- | :------- | :------- | :--- |
| Header | X-USER-ID | Number | 유저 ID |
| Header | X-ROOM-ID | String | 방 ID |
| PathVariable | token | String | 토큰값 |
  

##### 출력결과

| 필드 | 타입 | 설명 |
| :--- | :--- | :--- |
| amount | Number | 뿌린 총 금액 |
| givenAmount| Number | 받기 완료된 금액 |
| happyPeople| Array | 받은 사람, 받은 금액 정보 |

```json
{
    "createdDate": "2020-06-27T15:33:49.674943",
    "amount": 10000,
    "givenAmount": 3333,
    "happyPeople": [
        {
            "amount": 3333,
            "keepers": "6"
        }
    ]
}
```

##### 에러코드

| HTTP 코드 | 에러 코드 | 에러 메시지 |
| :--- | :--- | :--- |
| 401 | 401 | 인증 정보 없음 |
| 404 | TOKEN_ERROR_01 | 해당 토큰에 대한 정보를 찾을 수 없습니다. |
| 403 | GIFT_ERROR_06 | 뿌린 사람 자신만 조회를 할 수 있습니다. |
| 403 | GIFT_ERROR_07 | 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다. |
