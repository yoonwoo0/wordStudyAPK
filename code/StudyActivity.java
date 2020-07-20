package com.example.wordstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class StudyActivity extends Activity {

    Button bt_study, bt_test;
    Spinner sp_days;
    String day;
    int mode;

    String word_arr[] = {"gateway","transaction","indemnify","conpicuous","adjacent","profitable","executive","commission","qualifications","advent",
            "wage","insurance","commute","stock","crisis","flourish","conference","present","lasted","earnings",
            "capable","suspect","blame","remind","close off","impatient","interact","endow","inclination","incur",
            "initiative","inquiry","insolent","inspect","instance","instigate","intangible","intend to","intensify","intention",
            "interim","intriguing","iron things out","irrigation","isolation","itinerary","keep clear of","keep track of","lapse","legnthy",
            "lessen", "liable", "light refreshment","liquidate","mandate","manipulation","markedly","meager","means","merely",
            "merchandise","meteorological","meticulously","minutes","morale","municipality","negligence","novice","numerically","devise",
            "devote","descard","discerning","disclose","discourteous","discrepancy","dispensable","disperse","distinctively","distract",
            "diverge","dormitory","drastic","dwelling","ecology","eloquent","emerge","emission","emphasize","endorsement",
            "endowment","enhance","entrepreneurship","erect","essentially","estranged from","ethic","evaluate","exaggerated","exclusively",
            "exotic","execute","expel","expertise","exquisite","extension number","extensively","extent","extinctions","extracurricular",
            "abbreviate","accommodate","accuse A of B","acquaint","admantly","adequate","adhere","adhesive","alleviate","amendment",
            "anticipate","approve","aspect","aspire","assess","assume","assure","apparently","as to","assign",
            "associate","attendant","attentive","attraction","attribute","banquet","barring","be eligible for","be reluctant to","become acquaint with",
            "belated","bewilder","boast","by comparison","cautious","certificate","clarify","coincide","colleague","commit",
            "committable","companion","compartment","compatible","compliance","complimentary","comply with","comprehensive","conceal","concede",
            "conceivably","concerning","conduct","confide","confidentiality","congest","conscious","consecutive","consent","consequent",
            "extrairdinary","extravagant","extreme measure","fabrication","facilitate","fatal","feasibility","feature","fiasco","fine",
            "fixture","fluctuation","for the time being","formerly","forthcoming","fraud","freight","fringe benefit","frustrate","fulfill",
            "worrisome","prone","proportion","prosperous","provision","proximity","puddle","punctual","pursue",	"quote",
            "raw materials","reconcile","rectify","reference","refrain from ~ing","regulate","reimburse","rein","relevant","relieve",
            "reluctant", "remedy", "remit", "reoccurrence", "respective" ,"respirator", "retail", "retain", "retrieve", "revise",
            "ridicule","rigorous","rural","sanctions against", "shred", "shrub", "significant","sparsely","specify","standing ovation"};

    String mean_arr[] = {"수단 / 통로", "거래",  "~에게 배상(보상)하다 / 면제하다", "눈에띄는 / 똑똑히 보이는", "이웃한 / 인접한", "이윤이 남는", "행정의", "수당", "자격 조건", "출현",
            "(노동자의)임금", "보험", "통근하다", "증권 / 주식", "위기", "번창하다", "회의", "제출하다", "지속되다 / 계속되다", "수입 / 수익",
            "능력있는", "의심하다", "비난하다", "생각나게하다", "막다/차단하다", "참을성 없는/초조한", "서로 영향을 끼치다", "부여하다", "경향", "초래하다 /발생시키다",
            "주도 / 계획", "연구 / 문의", "버릇없는 / 무례한", "면밀하게 살피다 / 점검하다", "사례 / 경우", "부추기다 / 선동하다", "무형의", "~할 생각이다", "강화하다", "의도",
            "임시의", "흥미를 자아내는", "문제를 해결하다", "관계", "고립 / 고독", "여행 일정표", "~에서 떨어져 있다", "~에 대해 파악하고 있다", "착오 / 실수", "너무 긴/ 장황한",
            "줄이다/ 완화하다", "~하기 쉬운/ ~할 것 같은", "가벼운 다과", "처분하다 / 청산하다", "명령하다 / 요구하다", "조작", "두드러지게", "부족한/결핍된", "방도 / 방법", "단지",
            "상품", "기상의", "꼼꼼하게", "회의록 / 의사록", "사기", "지방자치단체", "부주의 / 태만", "초보자", "수적으로", "궁리하다 / 고안하다",
            "~에 바치다", "버리다", "식별력 있는 / 안목 있는", "밝히다 / 드러내다", "무례한", "차이 / 불일치", "없어도 되는", "해산시키다 / 분산시키다", "구별하여 / 특징적으로", "집중이 안 되게 하다",
            "분기하다 / 나눠지다", "기숙사", "강력한 / 과감함", "거주 / 거처", "생태", "설득력 있는 / 웅변력 있는", "드러나다 / 나타나다", "배출", "강조하다", "배서 / 보증",
            "기증 / 기부", "강화하다 / 높이다", "기업가 정신", "세우다", "본t질적으로", "~로부터 소원해진", "윤리", "평가하다 / 감정하다", "과장된", "배타적으로",
            "이국적인", "실행하다 / 집행하다", "내쫒다", "전문적 지식 / 기술", "정교한 / 더없이 훌륭한", "내선 번호", "광범위하게", "규모 / 범위", "멸종", "과외의 / 정규 과목 이외의",
            "축약하다 / 단축하다", "수용하다", "B를 이유로 A를 고발하다", "익히다 / 숙지하다", "확고하게", "적절한", "고수하다", "점착성의", "줄이다 / 완화하다", "개정",
            "예상하다", "승인하다 / 찬성하다", "면 / 측면", "열망하다", "평가하다 / 감정하다", "맡다 / 취하다", "보증하다 / 안심시키다", "외관상으로는 / 분명히", "~에 관하여", "임무를 할당하다",
            "관련시키다", "안내원 / 참석자", "주의 깊은 / 경청하는", "관광 명소", "~에 귀착시키다", "연회", "~이 없다면 / ~을 제외하고", "~에 대한 자격이 있다", "~하기를 꺼리다", "~와 알게되다",
            "때늦은", "당황하게 하다", "자랑하다", "비교해 보면", "조심성 있는 / 신중한", "자격정 / 자격증을 교부하다", "분명히 하다", "일치하다", "동료", "전념하다 / 헌신하다",
            "위탁할 수 있는", "동료 / 친구", "(물건 보관용)칸", "호환 가능한 / 양립될 수 있는", "준수", "무료의 / 칭찬하는", "규정을 준수하다", "포괄적인", "숨기다", "인정하다",
            "아마도 / 상상컨데", "~에 관하여", "수행하다 / 처리하다", "(비밀을)털어놓다", "기밀성", "혼잡하게 하다", "의식이 있는", "연속의 / 연이은", "동의/허가", "~의 결과로 일어나는",
            "보기 드문 / 탁월한", "낭비벽이 있는", "극단적인 조치", "제작", "가능하게 하다", "치명적인", "가능성 / 실행 가능함", "특징 / 특징으로 하다", "큰 실수 / 대실패", "벌금을 부과하다",
            "고정 세간 / 붙박이", "변동", "당분간", "이전에 / 예전에", "다가오는", "사기", "화물", "부가 혜택", "좌절시키다 / 실망시키다", "이행하다",
            "걱정스러운", "~하기 쉬운 / ~경향이 있는", "비율",	"번영하는 / 번창한", "조항 / 공급", "근접", "웅덩이", "시간을 지키는", "추구하다 / 밀고 나가다", "인용하다",
            "원자재", "화해시키다", "고치다 / 바로잡다", "추천서", "~을 그만두다 / 삼가다", "규제하다 / 조정하다", "변상하다 / 환급하다", "억제하다", "관련된", "경감하다",
            "꺼리는 / 주저하는", "바로 잡다", "송금하다 / 면제하다", "재발", "각각의", "인공 호흡장치", "소매의 / 소매하다", "보관하다", "회수하다", "정정하다 / 수정하다",
            "비웃다", "엄격한 / 혹독한", "시골의", "~에 대한 제재", "잘게 자르다 / 찢다", "관목", "의미있는 / 중대한", "희박하게 / 드문드문", "명시하다", "기립박수"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectday_study_test);

        bt_study = (Button)findViewById(R.id.btn_study_selectday);
        bt_test = (Button)findViewById(R.id.btn_test_selectday);
        sp_days = (Spinner)findViewById(R.id.sp_days);

        Intent gMode = getIntent();
        mode = gMode.getIntExtra("mode",0);

        sp_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = parent.getItemAtPosition(position).toString();
                day = day.replace("일차","");

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bt_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StudyActivity.this,WordbookActivity.class);
                myIntent.putExtra("days",day);
                myIntent.putExtra("word",word_arr);
                myIntent.putExtra("mean",mean_arr);
                myIntent.putExtra("mode",mode);
                startActivity(myIntent);
            }
        });

        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StudyActivity.this, TestActivity.class);
                myIntent.putExtra("days",day);
                myIntent.putExtra("word",word_arr);
                myIntent.putExtra("mean",mean_arr);
                myIntent.putExtra("mode",mode);
                startActivity(myIntent);
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(StudyActivity.this, MainActivity.class);
        homeIntent.putExtra("mode",mode);
        startActivity(homeIntent);
    }
}