import { View, Image } from "@tarojs/components";
import { AtButton } from "taro-ui";

import headerBg from "../../assets/headerBg.jpg";
import "./index.scss";
import GlobalFooter from "../../components/GlobalFooter";

export default () => {
  return (
    <View className="indexPage">
      <View className="at-article__h1 title">MBTI Personality Test</View>
      <View className="at-article__h2 subTitle1">Find your personality within TWO mins, the best free MBTI personality test！</View>
      <AtButton type="primary" circle className="enterTest">
        Start Test
      </AtButton>
      <Image className="headerBg" src={headerBg} />
      <GlobalFooter />
    </View>
  );
};
