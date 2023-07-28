package com.example.abschlussaufgabe.data.datamodels.modelForFight

class UniqueTrait(name: String, value: Int, image: Int): Attack(name, value, image) {

    init {
        isTrait = true
    }

    override fun subtractLifePoints(
        player: CharacterForFight,
        playerToChange: CharacterForFight,
        enemyAttack: Attack,
        enemyToChange: CharacterForFight,
        enemy: CharacterForFight,
        toast: (String) -> Unit) {

        if (name == "Heilung") {
            if (player.lifePoints < player.lifePointsStart) {
                playerToChange.lifePoints = player.lifePoints.plus(value)
                if (player.lifePoints > player.lifePointsStart) {
                    playerToChange.lifePoints = player.lifePointsStart
                }
            } else {
                return toast("Deine Lebenspunkte sind voll!")
            }
        } else {
            super.subtractLifePoints(player,
                playerToChange, enemyAttack, enemyToChange, enemy, toast)
        }
    }
}