enum class FillUpState { NOT_INIT, IN_PROGRESS, FULL }

data class Dir(
    val parent: Dir?,
    val name: String,
    var fillUpState: FillUpState = FillUpState.NOT_INIT,
    val files: MutableList<File> = mutableListOf(),
    val directories: MutableList<Dir> = mutableListOf(),
) {
    val size: Int by lazy { files.sumOf { it.size } + directories.sumOf { it.size } }
}
data class File(val name: String, val size: Int)

val root = Dir(
    parent = null,
    name = "/",
)
var currentDir = root

fun main() {
    fun changeDirectory(arg: String) {
        currentDir = when (arg) {
            root.name -> root
            ".." -> currentDir.parent ?: currentDir
            else -> currentDir.directories.single { it.name == arg }
        }
    }

    fun listContents(terminal: List<String>, i: Int): Int {
        currentDir.fillUpState = FillUpState.IN_PROGRESS
        val listIterator = terminal.listIterator(i + 1)
        while (listIterator.hasNext()) {
            val current = listIterator.next()
            if (current.startsWith("$")) {
                currentDir.fillUpState = FillUpState.FULL
                return listIterator.previousIndex()
            }
            if (currentDir.fillUpState != FillUpState.FULL) {
                val split = current.split(" ")
                when {
                    split[0] == "dir" -> currentDir.directories.add(Dir(currentDir, split[1]))
                    else -> currentDir.files.add(File(split[1], split[0].toInt()))
                }
            }
        }
        currentDir.fillUpState = FillUpState.FULL
        return listIterator.nextIndex()
    }

    fun parseTerminal(terminal: List<String>) {
        var i = 0
        while (i < terminal.size) {
            val currentCommand = terminal[i].split(" ")
            println ("Parsing index $i command: $currentCommand")
            i = when (currentCommand[1]) {
                "cd" -> {
                    changeDirectory(currentCommand[2])
                    i + 1
                }
                "ls" -> listContents(terminal, i)
                else -> throw IllegalStateException("Cannot handle command $currentCommand")
            }
            println("Next index is $i")
        }
    }

    fun iterateAllSubDirs(dir: Dir = root, toIterate: MutableList<Dir> = mutableListOf()): MutableList<Dir> {
        toIterate.add(dir)
        dir.directories.forEach {iterateAllSubDirs(it, toIterate)}
        return toIterate
    }

    fun getSumSizeLeq100K(): Int {
        return iterateAllSubDirs().asSequence()
            .map { it.size }
            .filter { it <= 100000 }
            .sum()
    }

    fun getSmallestDirToDelete(): Int {
        val maxSize = 40000000
        return iterateAllSubDirs().asSequence()
            .map { it.size }
            .filter { root.size - it <= maxSize }
            .min()
    }

    val input = readInput("Day07")
    parseTerminal(input)
    println(getSumSizeLeq100K())
    println(getSmallestDirToDelete())
}
