/**
 * Get the best topic scoring results
 * @param answerList
 * @param questions
 * @param question_results
 */
export function getBestQuestionResult(answerList, questions, question_results) {
  //Initialise an object to store the count for each option
  const optionCount = {};

  //User chose A, B, C
  //correspond result：I, I, J
  //optionCount[I] = 2; optionCount[J] = 1

  //Iterate through the topic list
  for (const question of questions) {
    //Iterate through the answer list
    for (const answer of answerList) {
      //Iterate through the choice list
      for (const option of question.options) {
        //If the answer matches the key of the option
        if (option.key === answer) {
          //Get the result property of the option
          const result = option.result;

          //If the result property is not in the optionCount object, add it with a count of 0
          if (!optionCount[result]) {
            optionCount[result] = 0;
          }

          //Increase count in optionCount
          optionCount[result]++;
        }
      }
    }
  }

  //Initialise the highest score and the scoring result corresponding to the highest score
  let maxScore = 0;
  let maxScoreResult = question_results[0];

  //Iterate through the scoring results
  for (const result of question_results) {
    //Get the score for the current result
    const score = result.resultProp.reduce((count, prop) => {
      return count + (optionCount[prop] || 0);
    }, 0);

    //If the score is higher than the current maximum score, update the maximum score and the rating result corresponding to the maximum score
    if (score > maxScore) {
      maxScore = score;
      maxScoreResult = result;
    }
  }

  //Returns the highest score and the rating result corresponding to the highest score
  return maxScoreResult;
}
