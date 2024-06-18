import { View, Image } from "@tarojs/components";
import { AtButton } from "taro-ui";
import Taro from "@tarojs/taro";
import headerBg from "../../assets/headerBg.jpg";
import questions from "../../data/questions.json";
import "./index.scss";
import GlobalFooter from "../../components/GlobalFooter";
import questionResults from "../../data/question_results.json";
import { getBestQuestionResult } from "../../utils/bizUtils";

/**
 * result page
 */
export default () => {
  //get answer
  const answerList = Taro.getStorageSync("answerList");
  if (!answerList || answerList.length < 1) {
    Taro.showToast({
      title: "Answer is null",
      icon: "error",
      duration: 3000,
    });
  }

  //get test result
  const result = getBestQuestionResult(answerList, questions, questionResults);

  return (
    <View className="resultPage">
      <View className="at-article__h1 title">{result.resultName}</View>
      <View className="at-article__h2 subTitle1">{result.resultDesc}</View>
      <AtButton
        type="primary"
        circle
        className="enterTest"
        onClick={() => {
          Taro.reLaunch({
            url: "/pages/index/index",
          });
        }}
      >
        Homepage
      </AtButton>
      <Image className="headerBg" src={headerBg} />
      <GlobalFooter />
    </View>
  );
};
