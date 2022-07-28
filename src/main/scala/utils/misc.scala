package utils

/** Maps the elements of a vector, passing through the log in each pass of the function
  * @param log
  *   the log
  * @param target
  *   the vector to map
  * @return
  *   the log and the resulting vector
  */
def mapWithLog[A, B](log: Log, target: Vector[A])(f: (Log, A) => (Log, B)) =
  target.foldLeft((log, Vector[B]()))((lgv, t) =>
    val (lg, v)      = lgv
    val (newlg, res) = f(lg, t)
    (newlg, v :+ res)
  )

/** FlatMaps (via Log) the elements of a vector, passing through the log in each pass of the
  * function
  * @param log
  *   the log
  * @param target
  *   the vector to flatMap
  * @param return
  *   the log and an optional vector
  */
def flatMapWithLog[A, B](log: Log, target: Vector[A])(f: (Log, A) => LogWithOption[B]) =
  target.foldLeft(LogWithOption(log, Some(Vector[B]())))((lwb, a) =>
    lwb.flatMap((log, vb) =>
      val g      = f(log, a)
      val newLog = g.log
      g.opt match
        case Some(b) => LogWithOption(newLog, Some(vb :+ b))
        case None    => LogWithOption(newLog, None)
    )
  )
