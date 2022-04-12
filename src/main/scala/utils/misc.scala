package utils

def mapWithLog[A, B](log: Log, target: Vector[A])(f: (Log, A) => (Log, B)) =
  target.foldLeft((log, Vector[B]()))((lgv, t) =>
    val (lg, v)      = lgv
    val (newlg, res) = f(lg, t)
    (newlg, v :+ res)
  )
