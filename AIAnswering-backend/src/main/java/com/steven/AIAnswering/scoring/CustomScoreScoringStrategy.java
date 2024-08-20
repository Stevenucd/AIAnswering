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
import java.util.List;
import java.util.Optional;

/**
 * Custom Score Scoring Strategy
 *
 */
@ScoringStrategyConfig(appType = 0, scoringStrategy = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy {

    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        Long appId = app.getId();
        // 1. Search for question and question result information based on id (sorted by score in descending order)
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class).eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );

        // 2. Total score of users
        int totalScore = 0;
        QuestionVO questionVO = QuestionVO.objToVo(question);
        List<QuestionContentDTO> questionContent = questionVO.getQuestionContent();

        // Iterate through the list of questions
        for (int i=0; i<questionContent.size(); i++) {
            QuestionContentDTO questionContentDTO = questionContent.get(i);
            String answer = choices.get(i);
            // Iterate through the options in the question
            for (QuestionContentDTO.Option option : questionContentDTO.getOptions()) {
                // If the answer matches the key of the option
                if (option.getKey().equals(answer)) {
                    int score = Optional.of(option.getScore()).orElse(0);
                    totalScore += score;
                }
            }
        }

        // 3. Iterate through the scoring results to find the first user score that is greater than the scoring range as the final result
        ScoringResult maxScoringResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList) {
            if (totalScore >= scoringResult.getResultScoreRange()) {
                maxScoringResult = scoringResult;
                break;
            }
        }

        // 4. Construct return values to populate the properties of the answer object
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoringResult.getId());
        userAnswer.setResultName(maxScoringResult.getResultName());
        userAnswer.setResultDesc(maxScoringResult.getResultDesc());
        userAnswer.setResultPicture(maxScoringResult.getResultPicture());
        userAnswer.setResultScore(totalScore);
        return userAnswer;
    }
}
