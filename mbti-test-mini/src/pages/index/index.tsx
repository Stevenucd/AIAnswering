import { View, Image } from "@tarojs/components";
import { AtButton } from "taro-ui";
import Taro from "@tarojs/taro";
import headerBg from "../../assets/headerBg.jpg";
import "./index.scss";
import GlobalFooter from "../../components/GlobalFooter";

/**
 * Homepage
 */
export default () => {
  return (
    <View className="indexPage">
      <View className="at-article__h1 title">MBTI Personality Test</View>
      <View className="at-article__h2 subTitle1">
        Find your personality within TWO mins, the best free MBTI personality
        test！
      </View>
      <AtButton
        type="primary"
        circle
        className="enterTest"
        onClick={() => {
          Taro.navigateTo({
            url: "/pages/doQuestions/index",
          });
        }}
      >
        Start Test
      </AtButton>
      <Image className="headerBg" src={headerBg} />
      <GlobalFooter />
    </View>
  );
};
