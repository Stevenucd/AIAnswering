import { View } from "@tarojs/components";
import Taro from "@tarojs/taro";
import { AtButton, AtRadio } from "taro-ui";
import { useEffect, useState } from "react";
import questions from "../../data/questions.json";
import GlobalFooter from "../../components/GlobalFooter";
import "./index.scss";

/**
 * do question pages
 */
export default () => {
  //question number start from 1.
  const [current, setCurrent] = useState<number>(1);
  //current question
  const [currentQuestion, setCurrentQuestion] = useState(questions[0]);
  const questionOptions = currentQuestion.options.map((option) => {
    return { label: `${option.key}. ${option.value}`, value: option.key };
  });
  //current answer
  const [currentAnswer, setCurrentAnswer] = useState<string>();
  //answer list
  const [answerList] = useState<string[]>([]);

  //when question number change, update question and answer.
  useEffect(() => {
    setCurrentQuestion(questions[current - 1]);
    setCurrentAnswer(answerList[current - 1]);
  }, [current]);

  return (
    <View className="doQuestionsPage">
      <View className="at-article__h2 title">
        {current}.{currentQuestion.title}
      </View>
      <View className="options-wrapper">
        <AtRadio
          options={questionOptions}
          value={currentAnswer}
          onClick={(value) => {
            setCurrentAnswer(value);
            //record answer
            answerList[current - 1] = value;
          }}
        />
      </View>
      {current < questions.length && (
        <AtButton
          type="primary"
          circle
          className="controlBtn"
          disabled={!currentAnswer}
          onClick={() => setCurrent(current + 1)}
        >
          Next
        </AtButton>
      )}
      {current == questions.length && (
        <AtButton
          type="primary"
          circle
          className="controlBtn"
          disabled={!currentAnswer}
          onClick={() => {
            //Passing on the answer
            Taro.setStorageSync("answerList", answerList);
            //jump to result
            Taro.navigateTo({
              url: "/pages/result/index",
            });
          }}
        >
          Show Result
        </AtButton>
      )}
      {current > 1 && (
        <AtButton
          circle
          className="controlBtn"
          onClick={() => setCurrent(current - 1)}
        >
          Previous
        </AtButton>
      )}
      <GlobalFooter />
    </View>
  );
};
