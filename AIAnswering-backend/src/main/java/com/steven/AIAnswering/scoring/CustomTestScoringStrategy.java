package com.steven.AIAnswering.scoring;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.steven.AIAnswering.model.dto.question.QuestionContentDTO;
import com.steven.AIAnswering.model.entity.App;
import com.steven.AIAnswering.model.entity.Question;
import com.steven.AIAnswering.model.entity.ScoringResult;
import com.steven.AIAnswering.model.entity.UserAnswer;
import com.steven.AIAnswering.model.vo.QuestionVO;
import com.steven.AIAnswering.service.QuestionService;
import com.steven.AIAnswering.service.ScoringResultService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom Test Scoring Strategy
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://www.code-nav.cn">编程导航学习圈</a>
 */
@ScoringStrategyConfig(appType = 1, scoringStrategy = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        // 1. Search for the question and question result information according to id.
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
        );

        // 2. Count the number of attributes for each user selection, e.g. I = 10, E = 5.
        // Initialise a Map to store the count of each option
        Map<String, Integer> optionCount = new HashMap<>();

        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        // Iterate through the list of questions
        for (QuestionContentDTO questionContentDTO : questionContent) {
            // Iterate through the list of answers
            for (String answer : choices) {
                // Iterate through the options in the question
                for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                    // If the answer matches the key of the option
                    if (option.getKey().equals(answer)) {
                        // Get the result property of the option
                        String result = option.getResult();

                        // If the result attribute is not in optionCount, initialised to 0
                        if (!optionCount.containsKey(result)) {
                            optionCount.put(result, 0);
                        }

                        // Increase count in optionCount
                        optionCount.put(result, optionCount.get(result) + 1);
                    }
                }
            }
        }

        // 3. Iterate through each scoring result and calculate which result has the higher score
        // Initialise the highest score and the scoring result corresponding to the highest score
        int maxScore = 0;
        ScoringResult maxScoringResult = scoringResultList.get(0);

        // Iterate through the list of scoring results
        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            // Calculate the score of the current scoring result, [I, E] => [10, 5] => 15
            int score = resultProp.stream()
                    .mapToInt(prop -> optionCount.getOrDefault(prop, 0))
                    .sum();

            // If the score is higher than the current maximum score, update the maximum score and the rating result corresponding to the maximum score
            if (score > maxScore) {
                maxScore = score;
                maxScoringResult = scoringResult;
            }
        }

        // 4. Construct the return value to populate the properties of the answer object
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        return userAnswer;
    }
}
