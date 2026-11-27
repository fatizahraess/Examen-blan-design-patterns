package net.essadiki.neuralnetwork.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

    @Around(
            "execution(* net.essadiki.neuralnetwork..*(..))"
                    + " && !within(net.essadiki.neuralnetwork.aspect..*)"
    )
    public Object logMethods(ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName =
                joinPoint.getSignature().toShortString();

        long start = System.nanoTime();

        System.out.println("[AOP LOG] Debut : "
                + methodName);

        try {

            Object result = joinPoint.proceed();

            System.out.println("[AOP LOG] Succes : "
                    + methodName);

            return result;

        } catch (Throwable e) {

            System.out.println("[AOP LOG] Erreur : "
                    + e.getMessage());

            throw e;

        } finally {

            double duration =
                    (System.nanoTime() - start)
                            / 1_000_000.0;

            System.out.printf(
                    "[AOP LOG] Duree : %.4f ms%n",
                    duration
            );
        }
    }
}