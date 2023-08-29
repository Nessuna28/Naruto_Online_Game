package com.example.abschlussaufgabe.data.datamodels.modelForFight


open class Attack(

    val name: String,
    val value: Int,
    val image: Int
) {

    var isDefense: Boolean = false
    var isTool: Boolean = false
    var isJutsu: Boolean = false
    var isTrait: Boolean = false

    open fun subtractChakra(
        playerToChange: CharacterForFight,
        player: CharacterForFight,
        toast: (String) -> Unit
    ) {

        if (!isTool) {
            if (player.chakraPoints >= value) {
                playerToChange.chakraPoints = player.chakraPoints.minus(value)
            } else {
                return toast("Du hast nicht genügend Chakra für diese Attacke!")
            }
        }
    }

    open fun loadChakra(playerToChange: CharacterForFight, player: CharacterForFight) {

        if (isTool) {
            if (player.chakraPoints < player.chakraPointsStart) {
                playerToChange.chakraPoints = player.chakraPoints.plus(10)
                if (player.chakraPoints > player.chakraPointsStart) {
                    playerToChange.chakraPoints = player.chakraPointsStart
                }
            }
        }
    }

    open fun subtractLifePoints(
        player: CharacterForFight,
        playerToChange: CharacterForFight,
        enemyAttack: Attack,
        enemyToChange: CharacterForFight,
        enemy: CharacterForFight,
        toast: (String) -> Unit
    ) {

        if (!enemyAttack.isDefense && enemy.lifePoints > 0) {
            enemyToChange.lifePoints = enemy.lifePoints.minus(value)
            if (enemy.lifePoints < 0) {
                enemyToChange.lifePoints = 0
            }
        }
    }
}